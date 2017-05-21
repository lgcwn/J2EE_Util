/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.OrderPayLog;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 支付记录接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IOrderPayLogService {
	
	void save(OrderPayLog orderPayLog);

    void update(OrderPayLog orderPayLog);
	
	void delete(java.lang.Long id);
	
    OrderPayLog getById(java.lang.Long id);

    Page<OrderPayLog> getPage(Map params, PageBounds rowBounds);
	

}
