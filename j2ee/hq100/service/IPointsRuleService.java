/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.PointsRule;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 积分规则接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IPointsRuleService {
	
	void save(PointsRule pointsRule);

    void update(PointsRule pointsRule);
	
	void delete(java.lang.Long id);
	
    PointsRule getById(java.lang.Long id);

    Page<PointsRule> getPage(Map params, PageBounds rowBounds);
	

}
