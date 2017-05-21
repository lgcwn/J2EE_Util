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

import com.up72.hq.service.IOrderLogService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 订单操作日志DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderLogServiceImpl implements  IOrderLogService{
	
	@Autowired
	private OrderLogMapper orderLogMapper;
	
	public void save(OrderLog orderLog){
		orderLogMapper.insert(orderLog);
	}

    public void update(OrderLog orderLog){
		orderLogMapper.update(orderLog);
	}
	
    public void delete(java.lang.Long id){
		orderLogMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public OrderLog getById(java.lang.Long id){
		return orderLogMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<OrderLog> getPage(Map params, PageBounds rowBounds){
        PageList list = orderLogMapper.findPage(params, rowBounds);
		return new Page<OrderLog>(list,list.getPagination());
	}
	

}
