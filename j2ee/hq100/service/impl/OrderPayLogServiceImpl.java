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

import com.up72.hq.service.IOrderPayLogService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 支付记录DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderPayLogServiceImpl implements  IOrderPayLogService{
	
	@Autowired
	private OrderPayLogMapper orderPayLogMapper;
	
	public void save(OrderPayLog orderPayLog){
		orderPayLogMapper.insert(orderPayLog);
	}

    public void update(OrderPayLog orderPayLog){
		orderPayLogMapper.update(orderPayLog);
	}
	
    public void delete(java.lang.Long id){
		orderPayLogMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public OrderPayLog getById(java.lang.Long id){
		return orderPayLogMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<OrderPayLog> getPage(Map params, PageBounds rowBounds){
        PageList list = orderPayLogMapper.findPage(params, rowBounds);
		return new Page<OrderPayLog>(list,list.getPagination());
	}
	

}
