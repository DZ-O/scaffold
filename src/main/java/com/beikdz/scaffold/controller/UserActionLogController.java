package com.beikdz.scaffold.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beikdz.scaffold.model.UserActionLog;
import com.beikdz.scaffold.repository.UserActionLogMapper;
import com.beikdz.scaffold.util.SensitiveUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.alibaba.excel.EasyExcel;

@RestController
@RequestMapping("/user-action-log")
public class UserActionLogController {
    @Resource
    private UserActionLogMapper logMapper;

    @GetMapping("/export")
    public void export(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            HttpServletResponse response) throws Exception {
        LambdaQueryWrapper<UserActionLog> qw = new LambdaQueryWrapper<>();
        if (userId != null) qw.eq(UserActionLog::getUserId, userId);
        if (action != null) qw.eq(UserActionLog::getAction, action);
        if (startTime != null) qw.ge(UserActionLog::getCreateTime, startTime);
        if (endTime != null) qw.le(UserActionLog::getCreateTime, endTime);
        List<UserActionLog> list = logMapper.selectList(qw);
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("user_action_log.csv", "UTF-8"));
        PrintWriter writer = response.getWriter();
        writer.println("id,userId,username,action,module,content,ip,ua,createTime");
        for (UserActionLog log : list) {
            writer.printf("%d,%d,%s,%s,%s,%s,%s,%s,%s\n",
                    log.getId(),
                    log.getUserId(),
                    log.getUsername(),
                    log.getAction(),
                    log.getModule(),
                    log.getContent(),
                    log.getIp(),
                    log.getUa(),
                    log.getCreateTime()
            );
        }
        writer.flush();
        writer.close();
    }

    @GetMapping("/export-excel")
    public void exportExcel(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            HttpServletResponse response) throws Exception {
        LambdaQueryWrapper<UserActionLog> qw = new LambdaQueryWrapper<>();
        if (userId != null) qw.eq(UserActionLog::getUserId, userId);
        if (action != null) qw.eq(UserActionLog::getAction, action);
        if (startTime != null) qw.ge(UserActionLog::getCreateTime, startTime);
        if (endTime != null) qw.le(UserActionLog::getCreateTime, endTime);
        List<UserActionLog> list = logMapper.selectList(qw);
        // 脱敏
        list.forEach(log -> {
            log.setUsername(SensitiveUtil.maskEmail(log.getUsername()));
            log.setContent(SensitiveUtil.maskPhone(log.getContent()));
        });
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=user_action_log.xlsx");
        EasyExcel.write(response.getOutputStream(), UserActionLog.class).sheet("行为日志").doWrite(list);
    }

    @GetMapping("/stats/action-count")
    public Map<String, Long> actionCount(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        LambdaQueryWrapper<UserActionLog> qw = new LambdaQueryWrapper<>();
        if (start != null) qw.ge(UserActionLog::getCreateTime, start.atStartOfDay());
        if (end != null) qw.le(UserActionLog::getCreateTime, end.plusDays(1).atStartOfDay());
        List<UserActionLog> list = logMapper.selectList(qw);
        Map<String, Long> map = new HashMap<>();
        for (UserActionLog log : list) {
            map.put(log.getAction(), map.getOrDefault(log.getAction(), 0L) + 1);
        }
        return map;
    }

    @GetMapping("/stats/user-active")
    public Map<Long, Long> userActive(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        LambdaQueryWrapper<UserActionLog> qw = new LambdaQueryWrapper<>();
        if (start != null) qw.ge(UserActionLog::getCreateTime, start.atStartOfDay());
        if (end != null) qw.le(UserActionLog::getCreateTime, end.plusDays(1).atStartOfDay());
        List<UserActionLog> list = logMapper.selectList(qw);
        Map<Long, Long> map = new HashMap<>();
        for (UserActionLog log : list) {
            if (log.getUserId() != null)
                map.put(log.getUserId(), map.getOrDefault(log.getUserId(), 0L) + 1);
        }
        return map;
    }

    @SaCheckLogin
    @GetMapping("/stats/action-trend")
    public Map<String, Long> actionTrend(
            @RequestParam String action,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        Map<String, Long> trend = new HashMap<>();
        LocalDate day = start;
        while (!day.isAfter(end)) {
            LambdaQueryWrapper<UserActionLog> qw = new LambdaQueryWrapper<>();
            qw.eq(UserActionLog::getAction, action)
              .ge(UserActionLog::getCreateTime, day.atStartOfDay())
              .lt(UserActionLog::getCreateTime, day.plusDays(1).atStartOfDay());
            long count = logMapper.selectCount(qw);
            trend.put(day.toString(), count);
            day = day.plusDays(1);
        }
        return trend;
    }
}
