/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.ChooseListResp;
import com.up72.hq.model.ChooseList;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * (评选名单)接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IChooseListService {
	
	void save(ChooseList chooseList);

    void update(ChooseList chooseList);
	
	void delete(java.lang.Long id);

    ChooseListResp getById(java.lang.Long id);

    Page<ChooseListResp> getPage(Map params, PageBounds rowBounds);
	

}
