/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.Praise;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 我的点赞接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IPraiseService {
	
	void save(Praise praise);

    void update(Praise praise);
	
	void delete(java.lang.Long id);
	
    Praise getById(java.lang.Long id);

    Praise getByParam(Map map);

    Page<Praise> getPage(Map params, PageBounds rowBounds);



}
