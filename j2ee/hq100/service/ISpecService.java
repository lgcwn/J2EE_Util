/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.Spec2Resp;
import com.up72.hq.dto.resp.SpecResp;
import com.up72.hq.model.Cat;
import com.up72.hq.model.Spec;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 规格接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface ISpecService {

    Spec save(Spec Spec);

    void update(Spec Spec);

    void delete(Long id);

    Spec getById(Long id);

    Page<Spec> getPage(Map params, PageBounds rowBounds);

    Page<SpecResp> getRespPage(Map<String, Object> params, PageBounds pageBounds);

    void saveResp(SpecResp attrResp);

    int cntUsing(Long id);

    void updateResp(SpecResp attrResp);

    SpecResp getRespById(Long id);

    List<SpecResp> getListByCat(Cat cat);

    List<Spec2Resp> getListByProdId(Long prodId);
	

}
