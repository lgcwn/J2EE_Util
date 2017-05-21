/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.AttrResp;
import com.up72.hq.model.Attr;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;
import com.up72.hq.model.Cat;


/**
 * 属性接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IAttrService {

    Attr save(Attr Attr);

    void update(Attr Attr);

    void delete(Long id);

    Attr getById(Long id);

    Page<Attr> getPage(Map params, PageBounds rowBounds);

    AttrResp saveResp(AttrResp attrResp);

    Page<AttrResp> getRespPage(Map<String, Object> params, PageBounds pageBounds);

    AttrResp getRespById(Long id);

    void updateResp(AttrResp attrResp);

    int cntUsing(Long id);

    List<AttrResp> getListByCat(Cat cat);

    /**
     * 获取并排序
     * @param ids
     * @return
     */
    public List<Attr> getAndOrderByIds(String ids);
	

}
