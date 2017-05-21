/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.CatBrand;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 品牌分类关系接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface ICatBrandService {
	
	void save(CatBrand catBrand);

    void update(CatBrand catBrand);
	
	void delete(java.lang.Long id);
	
    CatBrand getById(java.lang.Long id);

    Page<CatBrand> getPage(Map params, PageBounds rowBounds);
	

}
