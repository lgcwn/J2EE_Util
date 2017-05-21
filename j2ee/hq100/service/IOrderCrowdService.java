/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.OrderCrowdResp;
import com.up72.hq.model.OrderCrowd;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 众筹商品接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IOrderCrowdService {
	
	void save(OrderCrowd orderCrowd);

    void update(OrderCrowd orderCrowd);
	
	void delete(java.lang.Long id);
	
    OrderCrowd getById(java.lang.Long id);

    Page<OrderCrowd> getPage(Map params, PageBounds rowBounds);

    Page<OrderCrowdResp> getPageResp(Map params, PageBounds rowBounds);

    OrderCrowdResp getByOrderId(java.lang.Long orderId);


}
