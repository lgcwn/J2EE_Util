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

import com.up72.hq.service.IOrderRefundLogService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

/**
 * DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderRefundLogServiceImpl implements  IOrderRefundLogService{
	
	@Autowired
	private OrderRefundLogMapper orderRefundLogMapper;
	
	public void save(OrderRefundLog orderRefundLog){
		orderRefundLogMapper.insert(orderRefundLog);
	}

    public void update(OrderRefundLog orderRefundLog){
		orderRefundLogMapper.update(orderRefundLog);
	}
	
    public void delete(java.lang.Long id){
		orderRefundLogMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public OrderRefundLog getById(java.lang.Long id){
		return orderRefundLogMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<OrderRefundLog> getPage(Map params, PageBounds rowBounds){
        PageList list = orderRefundLogMapper.findPage(params, rowBounds);
		return new Page<OrderRefundLog>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public OrderRefundLog getByRefundTradeNo(String refundTradeNo) {
		return orderRefundLogMapper.findByRefundTradeNo(refundTradeNo);
	}


}
