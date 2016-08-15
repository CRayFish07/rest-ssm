/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.alsguo.common.web.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.alsguo.common.service.BaseService;

/**
 * 基础CRUD 控制器
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-23 下午1:20
 * <p>Version: 1.0
 */
public abstract class BaseCRUDController<M extends Serializable, ID extends Serializable>
        extends BaseController<M, ID> {

    protected BaseService<M> baseService;

    /**
     * 设置基础service
     *
     * @param baseService
     */
    @Autowired
    public void setBaseService(BaseService<M> baseService) {
        this.baseService = baseService;
    }
}
