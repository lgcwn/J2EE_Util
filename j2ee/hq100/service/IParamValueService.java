/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.ParamValue;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 参数值接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IParamValueService {

    ParamValue save(ParamValue ParamValue);

    void update(ParamValue ParamValue);

    void delete(Long id);

    ParamValue getById(Long id);

    Page<ParamValue> getPage(Map params, PageBounds rowBounds);

    int cntUsing(Long paramValueId);
	

}
