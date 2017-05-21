/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.ParamResp;
import com.up72.hq.model.Cat;
import com.up72.hq.model.Param;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 参数接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IParamService {

    Param save(Param Param);

    void update(Param Param);

    void delete(Long id);

    Param getById(Long id);

    Page<Param> getPage(Map params, PageBounds rowBounds);

    Page<ParamResp> getRespPage(Map<String, Object> params, PageBounds pageBounds);

    void saveResp(ParamResp paramResp);

    void updateResp(ParamResp paramResp);

    ParamResp getRespById(Long id);

    int cntUsing(Long paramId);

    List<ParamResp> getListByCat(Cat cat);
	

}
