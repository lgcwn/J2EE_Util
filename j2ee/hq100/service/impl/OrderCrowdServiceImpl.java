/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.hq.dto.resp.OrderCrowdResp;
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

import com.up72.hq.service.IOrderCrowdService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * 众筹商品DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderCrowdServiceImpl implements  IOrderCrowdService{
	
	@Autowired
	private OrderCrowdMapper orderCrowdMapper;
	
	public void save(OrderCrowd orderCrowd){
		orderCrowdMapper.insert(orderCrowd);
	}

    public void update(OrderCrowd orderCrowd){
		orderCrowdMapper.update(orderCrowd);
	}
	
    public void delete(java.lang.Long id){
		orderCrowdMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public OrderCrowd getById(java.lang.Long id){
		return orderCrowdMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<OrderCrowd> getPage(Map params, PageBounds rowBounds){
        PageList list = orderCrowdMapper.findPage(params, rowBounds);
		return new Page<OrderCrowd>(list,list.getPagination());
	}

	@Override
	public Page<OrderCrowdResp> getPageResp(Map params, PageBounds rowBounds) {
		PageList list = orderCrowdMapper.findPageResp(params, rowBounds);
		return new Page<OrderCrowdResp>(list,list.getPagination());
	}

	@Override
	public OrderCrowdResp getByOrderId(Long orderId) {
		return orderCrowdMapper.findByOrderId(orderId);
	}


}
