/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.AdvertisingInfo;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * 广告信息接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IAdvertisingInfoService {
	
	void save(AdvertisingInfo advertisingInfo);

    void update(AdvertisingInfo advertisingInfo);
	
	void delete(java.lang.Long id);
	void delete(long[] id);

    AdvertisingInfo getById(java.lang.Long id);

    Page<AdvertisingInfo> getPage(Map params, PageBounds rowBounds);

    List<AdvertisingInfo> getListByColumnsAndTypes(Integer columns,Integer types);

}
