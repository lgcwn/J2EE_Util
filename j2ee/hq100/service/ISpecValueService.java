/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.SpecValue;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 规格值接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface ISpecValueService {

    SpecValue save(SpecValue SpecValue);

    void update(SpecValue SpecValue);

    void delete(Long id);

    SpecValue getById(Long id);

    Page<SpecValue> getPage(Map params, PageBounds rowBounds);

    List<SpecValue> getBySpecId(Long specId);


    int cntUsing(Long specValueId);
	

}
