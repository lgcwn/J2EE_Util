/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.ProdAttrResp;
import com.up72.hq.model.ProdAttr;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 产品属性中间表接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IProdAttrService {
	
	void save(ProdAttr prodAttr);

    void update(ProdAttr prodAttr);
	
	void delete(java.lang.Long id);
	
    ProdAttr getById(java.lang.Long id);

    Page<ProdAttr> getPage(Map params, PageBounds rowBounds);

    List<ProdAttrResp> getByAttrIdValId(Map map);
	

}
