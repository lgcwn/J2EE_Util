/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.ProdParam;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 产品参数中间表接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IProdParamService {
	
	void save(ProdParam prodParam);

    void update(ProdParam prodParam);
	
	void delete(java.lang.Long id);
	
    ProdParam getById(java.lang.Long id);

    Page<ProdParam> getPage(Map params, PageBounds rowBounds);
	

}
