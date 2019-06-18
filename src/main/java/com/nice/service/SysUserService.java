package com.nice.service;

import com.nice.domain.SysUser;
import org.springframework.transaction.annotation.Transactional;

public interface SysUserService {

    void addSysUser(SysUser sysUser);
}
