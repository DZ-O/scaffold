package com.beikdz.scaffold.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beikdz.scaffold.model.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
