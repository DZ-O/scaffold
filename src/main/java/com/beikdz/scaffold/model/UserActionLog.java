package com.beikdz.scaffold.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_action_log")
public class UserActionLog {
    private Long id;
    private Long userId;
    private String username;
    private String action;
    private String module;
    private String content;
    private String ip;
    private String ua;
    private Long tenantId;
    private Long orgId;
    private String extJson;
    private LocalDateTime createTime;
}
