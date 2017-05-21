/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.ExchangePoints;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 人名币兑换积分接口
 * 
 * @author up72
 * @version 1.0
 * @since 1.0
 */
public interface IExchangePointsService {
	
	void save(ExchangePoints exchangePoints);

    void update(ExchangePoints exchangePoints);
	
	void delete(java.lang.Long id);
	
    ExchangePoints getById(java.lang.Long id);

    Page<ExchangePoints> getPage(Map params, PageBounds rowBounds);
	

}
