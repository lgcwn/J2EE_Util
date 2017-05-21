/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.Region;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 区域字典接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IRegionService {
	
	void save(Region region);

    void update(Region region);
	
	void delete(java.lang.Long id);
	
    Region getById(java.lang.Long id);

    Page<Region> getPage(Map params, PageBounds rowBounds);
	

}
