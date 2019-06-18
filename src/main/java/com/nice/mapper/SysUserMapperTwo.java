package com.nice.mapper;

import com.nice.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ningh
 */
@Mapper
public interface SysUserMapperTwo {

    void addUser(SysUser sysUser);

}
