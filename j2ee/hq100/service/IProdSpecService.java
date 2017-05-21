/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.ProdSpecResp;
import com.up72.hq.model.ProdSpec;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * 产品规格中间表接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IProdSpecService {

    ProdSpec save(ProdSpec dhsProdSpec);

    void update(ProdSpec dhsProdSpec);

    void delete(Long id);

    ProdSpec getById(Long id);

    ProdSpecResp getByParams(Map map);

    Page<ProdSpec> getPage(Map params, PageBounds rowBounds);

    List<ProdSpec> getProdSpecByGoodsId(Long goodsId);

    List<ProdSpecResp> getProdSpecByProdId(Long prodId);

    String getSpecValueIdsByGoodsId(Map params);

    Boolean getIsUseSpec(HttpServletRequest request);

    List<ProdSpecResp> getColorListByProdId(Map map);

}
