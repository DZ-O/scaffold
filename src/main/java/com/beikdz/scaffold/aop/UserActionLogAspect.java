package com.beikdz.scaffold.aop;

import com.beikdz.scaffold.annotation.UserActionLogAnno;
import com.beikdz.scaffold.enums.UserActionType;
import com.beikdz.scaffold.model.UserActionLog;
import com.beikdz.scaffold.repository.UserActionLogMapper;
import com.beikdz.scaffold.repository.UserActionLogWhiteMapper;
import com.beikdz.scaffold.util.SensitiveUtil;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import jakarta.annotation.PostConstruct;
import org.springframework.util.StringUtils;

@Slf4j
@Aspect
@Component
public class UserActionLogAspect {
    private final UserActionLogMapper logMapper;
    private final UserActionLogWhiteMapper whiteMapper;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
    @Value("${user-action-log.ignore-users:}")
    private String ignoreUsersStr;
    @Value("${user-action-log.ignore-actions:}")
    private String ignoreActionsStr;
    @Value("${user-action-log.ignore-uris:}")
    private String ignoreUrisStr;

    private String[] ignoreUsers;
    private String[] ignoreActions;
    private String[] ignoreUris;

    @PostConstruct
    public void init() {
        ignoreUsers = StringUtils.hasLength(ignoreUsersStr) ? ignoreUsersStr.split(",") : new String[0];
        ignoreActions = StringUtils.hasLength(ignoreActionsStr) ? ignoreActionsStr.split(",") : new String[0];
        ignoreUris = StringUtils.hasLength(ignoreUrisStr) ? ignoreUrisStr.split(",") : new String[0];
    }
    public UserActionLogAspect(UserActionLogMapper logMapper, UserActionLogWhiteMapper whiteMapper) {
        this.logMapper = logMapper;
        this.whiteMapper = whiteMapper;
    }

    @AfterReturning("@annotation(com.beikdz.scaffold.annotation.UserActionLogAnno)")
    public void afterReturning(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            UserActionLogAnno annotation = method.getAnnotation(UserActionLogAnno.class);
            if (annotation.ignore()) return; // 白名单
            UserActionType actionType = annotation.action();
            UserActionLog logEntity = new UserActionLog();
            // 用户信息
            if (StpUtil.isLogin()) {
                logEntity.setUserId(StpUtil.getLoginIdAsLong());
                logEntity.setUsername(String.valueOf(StpUtil.getLoginId()));
                // 可扩展：从会话获取tenantId/orgId
            }
            // 注解参数
            logEntity.setAction(actionType.name());
            logEntity.setModule(annotation.module());
            // SpEL表达式解析
            String content = annotation.content();
            if (content != null && content.contains("#")) {
                StandardEvaluationContext context = new MethodBasedEvaluationContext(null, method, joinPoint.getArgs(), nameDiscoverer);
                logEntity.setContent(parser.parseExpression(content).getValue(context, String.class));
            } else {
                logEntity.setContent(content);
            }
            // 请求信息
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                logEntity.setIp(request.getRemoteAddr());
                logEntity.setUa(request.getHeader("User-Agent"));
            }
            logEntity.setCreateTime(LocalDateTime.now());
            // 配置白名单
            Set<String> ignoreUserSet = new HashSet<>(Arrays.asList(ignoreUsers));
            Set<String> ignoreActionSet = new HashSet<>(Arrays.asList(ignoreActions));
            Set<String> ignoreUriSet = new HashSet<>(Arrays.asList(ignoreUris));
            // 数据库白名单
            whiteMapper.selectList(null).forEach(w -> {
                if ("user".equals(w.getType())) ignoreUserSet.add(w.getValue());
                if ("action".equals(w.getType())) ignoreActionSet.add(w.getValue());
                if ("uri".equals(w.getType())) ignoreUriSet.add(w.getValue());
            });
            // 判断是否在白名单
            String currentUser = StpUtil.isLogin() ? String.valueOf(StpUtil.getLoginId()) : null;
            String currentAction = actionType.name();
            String currentUri = null;
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                currentUri = request.getRequestURI();
            }
            if ((currentUser != null && ignoreUserSet.contains(currentUser)) ||
                (currentAction != null && ignoreActionSet.contains(currentAction)) ||
                (currentUri != null && ignoreUriSet.contains(currentUri))) {
                return;
            }
            // 脱敏处理
            logEntity.setUsername(SensitiveUtil.maskEmail(logEntity.getUsername()));
            logEntity.setContent(SensitiveUtil.maskPhone(logEntity.getContent()));
            asyncInsert(logEntity);
        } catch (Exception e) {
            log.warn("用户行为日志埋点异常", e);
        }
    }

    // 添加事务支持
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    public void asyncInsert(UserActionLog logEntity) {
        try {
            logMapper.insert(logEntity);
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }


//    // 添加日志清理方法
//    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行
//    public void cleanOldLogs() {
//        try {
//            LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
//            LambdaQueryWrapper<UserActionLog> qw = new LambdaQueryWrapper<>();
//            qw.lt(UserActionLog::getCreateTime, threeMonthsAgo);
//            logMapper.delete(qw);
//            log.info("清理3个月前的操作日志完成");
//        } catch (Exception e) {
//            log.error("清理操作日志失败", e);
//        }
//    }
}
