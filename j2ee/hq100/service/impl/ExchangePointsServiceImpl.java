/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IExchangePointsService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 人名币兑换积分DAO实现
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class ExchangePointsServiceImpl implements  IExchangePointsService{
	
	@Autowired
	private ExchangePointsMapper exchangePointsMapper;
	
	public void save(ExchangePoints exchangePoints){
		exchangePointsMapper.insert(exchangePoints);
	}

    public void update(ExchangePoints exchangePoints){
		exchangePointsMapper.update(exchangePoints);
	}
	
    public void delete(java.lang.Long id){
		exchangePointsMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public ExchangePoints getById(java.lang.Long id){
		return exchangePointsMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<ExchangePoints> getPage(Map params, PageBounds rowBounds){
        PageList list = exchangePointsMapper.findPage(params, rowBounds);
		return new Page<ExchangePoints>(list,list.getPagination());
	}
	

}
