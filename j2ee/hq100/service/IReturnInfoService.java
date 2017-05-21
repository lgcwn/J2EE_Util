/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.ReturnInfoResp;
import com.up72.hq.model.ReturnInfo;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 回报信息接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IReturnInfoService {
	
	void save(ReturnInfo returnInfo);

    void update(ReturnInfo returnInfo);
	
	void delete(java.lang.Long id);
	void deleteByCrowdId(java.lang.Long id);

    ReturnInfo getById(java.lang.Long id);

    Page<ReturnInfo> getPage(Map params, PageBounds rowBounds);

    List<ReturnInfoResp> getByCrowdId(java.lang.Long crowdId);
}
