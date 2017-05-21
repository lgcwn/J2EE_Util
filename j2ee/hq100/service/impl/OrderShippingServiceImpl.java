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

import com.up72.hq.service.IOrderShippingService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 发货管理DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderShippingServiceImpl implements  IOrderShippingService{
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	public void save(OrderShipping orderShipping){
		orderShippingMapper.insert(orderShipping);
	}

    public void update(OrderShipping orderShipping){
		orderShippingMapper.update(orderShipping);
	}
	
    public void delete(java.lang.Long id){
		orderShippingMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public OrderShipping getById(java.lang.Long id){
		return orderShippingMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<OrderShipping> getPage(Map params, PageBounds rowBounds){
        PageList list = orderShippingMapper.findPage(params, rowBounds);
		return new Page<OrderShipping>(list,list.getPagination());
	}
	

}
