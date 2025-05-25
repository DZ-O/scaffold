package com.beikdz.scaffold.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.beikdz.scaffold.model.UserActionLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserActionLogMapper extends BaseMapper<UserActionLog> {
}
