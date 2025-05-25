package com.beikdz.scaffold.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beikdz.scaffold.model.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
