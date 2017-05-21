/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.Prod2Resp;
import com.up72.hq.dto.resp.ProdResp;
import com.up72.hq.model.Prod;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;


/**
 * 产品接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IProdService {

    Prod save(Prod dhsProd);

    void update(Prod dhsProd);

    void delete(Long id);

    Prod getById(Long id);

    Page<Prod> getPage(Map params, PageBounds rowBounds);

    ProdResp saveResp(ProdResp prodResp);

    void saveRespAttrParamImg(ProdResp prodResp);

    ProdResp getRespById(Long id);

    ProdResp updateResp(ProdResp prodResp);

    Page<Prod2Resp> getResp2Page(Map<String, Object> params, PageBounds pageBounds);

    Page<ProdResp> getRespPage(Map<String, Object> params, PageBounds pageBounds);

    void updateIsMarketable(Prod dhsProd);

    Double getMinPriceById(Long id);

    Double getMaxPriceById(Long id);
    /**
     * 获取 idPath 下 所有商品
     * @author haiyi
     * @param params
     * @param rowBounds
     * @return
     */
    Page<ProdResp> getListByCatIdPath(Map params, PageBounds rowBounds);
    /**
     * 获取产品是否可上架
     * @author haiyi
     * @param prodId
     * @return
     */
    String isUseMarke(Long prodId);
    /**
     * 商品Id获取产品
     * @author haiyi
     * @param goodsId
     * @return
     */
    ProdResp getByGoodsId(Long goodsId);


    /**
     * 更新产品的全规格内容、销量、评论等
     * @param prod
     */
    public void updateFull(Prod prod);

    /**
     * 更新产品总评论数
     */
    public void updateCommentCount();

    /**
     * 更新产品总库存(已上架的)
     * @param prodId
     */
    void updateStock(Long prodId);
	

}
