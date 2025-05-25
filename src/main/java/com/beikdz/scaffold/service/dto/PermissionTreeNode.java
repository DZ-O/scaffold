package com.beikdz.scaffold.service.dto;

import lombok.Data;
import java.util.List;

@Data
public class PermissionTreeNode {
    private Long id;
    private String name;
    private String code;
    private String type;
    private List<PermissionTreeNode> children;
}
