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

import com.up72.hq.service.IOrderShippingInfoService;
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
public class OrderShippingInfoServiceImpl implements  IOrderShippingInfoService{
	
	@Autowired
	private OrderShippingInfoMapper orderShippingInfoMapper;
	
	public void save(OrderShippingInfo orderShippingInfo){
		orderShippingInfoMapper.insert(orderShippingInfo);
	}

    public void update(OrderShippingInfo orderShippingInfo){
		orderShippingInfoMapper.update(orderShippingInfo);
	}
	
    public void delete(java.lang.Long id){
		orderShippingInfoMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public OrderShippingInfo getById(java.lang.Long id){
		return orderShippingInfoMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<OrderShippingInfo> getPage(Map params, PageBounds rowBounds){
        PageList list = orderShippingInfoMapper.findPage(params, rowBounds);
		return new Page<OrderShippingInfo>(list,list.getPagination());
	}
	

}
