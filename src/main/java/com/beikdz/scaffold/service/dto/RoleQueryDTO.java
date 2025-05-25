package com.beikdz.scaffold.service.dto;

import lombok.Data;

/**
 * 角色查询条件DTO
 */
@Data
public class RoleQueryDTO {
    private String code;
    private String name;
    private Integer status;
    private Long tenantId;
}
