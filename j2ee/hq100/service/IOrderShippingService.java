/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.OrderShipping;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 发货管理接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IOrderShippingService {
	
	void save(OrderShipping orderShipping);

    void update(OrderShipping orderShipping);
	
	void delete(java.lang.Long id);
	
    OrderShipping getById(java.lang.Long id);

    Page<OrderShipping> getPage(Map params, PageBounds rowBounds);
	

}
