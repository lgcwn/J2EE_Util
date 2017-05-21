/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.AppVersion;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 版本管理接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IAppVersionService {
	
	void save(AppVersion appVersion);

    void update(AppVersion appVersion);
	
	void delete(java.lang.Long id);
	
    AppVersion getById(java.lang.Long id);

    Page<AppVersion> getPage(Map params, PageBounds rowBounds);

    /**
     * 获取最新版本
     * @param osType
     * @return
     */
    AppVersion getLastestVersionByType(Integer osType);
	

}
