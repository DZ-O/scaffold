package com.beikdz.scaffold.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beikdz.scaffold.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
