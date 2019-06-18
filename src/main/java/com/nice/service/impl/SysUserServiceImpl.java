package com.nice.service.impl;

import com.nice.domain.SysUser;
import com.nice.mapper.SysUserMapperOne;
import com.nice.mapper.SysUserMapperTwo;
import com.nice.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ningh
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapperOne sysUserMapperOne;

    @Resource
    private SysUserMapperTwo sysUserMapperTwo;

    /**
     * @param sysUser
     */
    @Override
    @Transactional
    public void addSysUser(SysUser sysUser) {
        sysUserMapperOne.addUser(sysUser);
        int i = 1/0;
        sysUserMapperTwo.addUser(sysUser);
    }

}
