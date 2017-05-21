/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.AdvertisingManagementResp;
import com.up72.hq.model.AdvertisingManagement;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 广告位管理接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IAdvertisingManagementService {
	
	void save(AdvertisingManagement advertisingManagement);

    void update(AdvertisingManagement advertisingManagement);
	
	void delete(java.lang.Long id);
	
    AdvertisingManagement getById(java.lang.Long id);

    Page<AdvertisingManagement> getPage(Map params, PageBounds rowBounds);
    /*获取轮播图*/
    Page<AdvertisingManagementResp> getRespPage(Map params, PageBounds rowBounds);
}
