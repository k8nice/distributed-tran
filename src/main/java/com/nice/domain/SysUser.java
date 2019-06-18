package com.nice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ningh
 */
@Data
//@AllArgsConstructor
public class SysUser implements Serializable {
    private Long id;
    private String name;

    public SysUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
