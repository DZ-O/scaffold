package com.beikdz.scaffold.service.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户查询条件DTO
 */
@Data
public class UserQueryDTO {
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Integer gender;
    private Integer status;
    private Long orgId;
    private Long tenantId;
    private LocalDateTime createTimeStart;
    private LocalDateTime createTimeEnd;
}
