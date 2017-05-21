/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.RuleManagerOrderResp;
import com.up72.hq.model.RuleManagerOrder;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * 角色报名订单接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IRuleManagerOrderService {
	
	void save(RuleManagerOrder ruleManagerOrder);

    void update(RuleManagerOrder ruleManagerOrder);
	
	void delete(java.lang.Long id);

    RuleManagerOrderResp getById(java.lang.Long id);

    Page<RuleManagerOrderResp> getPage(Map params, PageBounds rowBounds);

    Long countNumberByStatus(Integer status,Long roleSelectId);

    String wxpayInstantCallback(HttpServletRequest request);

    RuleManagerOrder getBySn(String sn);

    String alipayInstantCallback(HttpServletRequest request);

    String wapAlipayInstantCallback(HttpServletRequest request);

    boolean loginUserIsSignUp(Map params);

}
