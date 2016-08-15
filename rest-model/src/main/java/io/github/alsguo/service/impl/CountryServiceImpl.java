package io.github.alsguo.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import io.github.alsguo.common.service.BaseService;
import io.github.alsguo.entity.Country;
import io.github.alsguo.service.CountryService;
import tk.mybatis.mapper.entity.Example;

@Service
public class CountryServiceImpl extends BaseService<Country> implements CountryService {

	public List<Country> selectByPage(Country country, int page, int rows) {
		Example example = new Example(entityClass);
		return selectByPage(example, page, rows);
//        PageHelper.startPage(page, rows);
//        return selectByExample(example);
	}

}
