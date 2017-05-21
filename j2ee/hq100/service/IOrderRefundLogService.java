/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.OrderRefundLog;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IOrderRefundLogService {
	
	void save(OrderRefundLog orderRefundLog);

    void update(OrderRefundLog orderRefundLog);
	
	void delete(java.lang.Long id);
	
    OrderRefundLog getById(java.lang.Long id);

    Page<OrderRefundLog> getPage(Map params, PageBounds rowBounds);

    OrderRefundLog getByRefundTradeNo(String refundTradeNo);
	

}
