package io.github.alsguo.service;

import java.util.List;

import io.github.alsguo.common.service.IService;
import io.github.alsguo.entity.Country;

public interface CountryService extends IService<Country> {

    /**
     * 根据条件分页查询
     *
     * @param country
     * @param page
     * @param rows
     * @return
     */
    List<Country> selectByPage(Country country, int page, int rows);
}
