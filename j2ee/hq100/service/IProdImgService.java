/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.ProdImg;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 产品颜色图片接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IProdImgService {
	
	void save(ProdImg prodImg);

    void update(ProdImg prodImg);
	
	void delete(java.lang.Long id);
	
    ProdImg getById(java.lang.Long id);

    Page<ProdImg> getPage(Map params, PageBounds rowBounds);

    /**
     * 查找产品下所有规格图片
     * @param prodId
     * @return
     */
    public List<ProdImg> getListByProdId(Long prodId);

    ProdImg getByParams(Map map);
	

}
