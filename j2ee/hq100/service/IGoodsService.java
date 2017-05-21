/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.Goods2Resp;
import com.up72.hq.dto.resp.Goods3Resp;
import com.up72.hq.dto.resp.GoodsResp;
import com.up72.hq.dto.resp.ProdResp;
import com.up72.hq.model.Cat;
import com.up72.hq.model.Goods;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;
import com.up72.hq.model.ProdSpec;
import com.up72.hq.model.Region;

import javax.servlet.http.HttpServletRequest;


/**
 * 商品接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IGoodsService {

    Goods save(Goods goods);

    void update(Goods goods);

    void delete(long id);
    void delete(long[] id);

    GoodsResp getById(Long id);

    Page<GoodsResp> getPage(Map params, PageBounds rowBounds);

    List<Goods> getList(Map params);

    Page<Goods3Resp> getResp3Page(Map params, PageBounds rowBounds);

    Goods2Resp getResp2ById(Long id);

    List<GoodsResp> getListByProdId(Long prodId);

    List<GoodsResp> getByProdId(Map map);

    String getGoodsIdsByProdId(Long prodId);

    Page<Goods> getHomePage(Map params, PageBounds rowBounds);

    Page<GoodsResp> getRespPage(Map params, PageBounds rowBounds);

    Page<GoodsResp> getRecommendGoodsPage(Map params, PageBounds rowBounds);

    Page<GoodsResp> getRecommendGoodsLists(HttpServletRequest request);

    Page<GoodsResp> getRecommendGoodsListss(HttpServletRequest request);

    List<Goods> getListByGoodsIds(Map params);

    List<Goods> getTop10OfToday();

    String getSpecName(String specStr, String specValStr);

    String getCatIdsStr(Cat cat);

    /**
     * 处理待编辑商品
     * @author haiyi
     * @param request
     * @param model
     * @return
     */
    Integer doEditWaitGoods(HttpServletRequest request, Goods model);
    /**
     * 编辑商品
     * @author haiyi
     * @param request
     * @param model
     * @return
     */
    Boolean doEditGoods(HttpServletRequest request, Goods model);
    /**
     * 获取Resp
     * @author haiyi
     * @param id
     * @return
     */
    GoodsResp getRespById(Long id);

    /**
     * 获取置顶序号
     * @author haiyi
     * @param catId
     * @return
     */
    Long getMinSort(Long catId);
    /**
     * 获取移动商品
     * @author haiyi
     * @param catId
     * @param goodsId
     * @return
     */
    Goods getMoveSortAction(Long catId,Long goodsId,Integer type);
    /**
     * 获取热销商品
     * @author haiyi
     * @param catId
     * @return
     */
    List<GoodsResp> getListByCatId(Long catId);
    /**
     * 根据名称获取商品
     * @author haiyi
     * @param  fullName
     * @return
     */
    List<Goods> getListByGoodsName(String fullName);
    /**
     * 根据产品获取商品
     * @author haiyi
     * @param prodResp
     * @return
     */
    Map<String,Object> getGoodsListByProd(ProdSpec prodResp,Region region);
    /**
     * 根据地区查询商品
     * @author haiyi
     * @param prodSpec
     * @param dhsRegion
     * @return
     */
    ProdResp setGoodsList(ProdResp prodSpec,Region dhsRegion);
    /**
     * 查询用户收藏商品列表
     * @author haiyi
     * @param map
     * @param request
     * @return
     */
    List<GoodsResp> getMemberFavouriteList(Map map,HttpServletRequest request,PageBounds pageBounds);

    public String getCatNamesStr(Cat dhsCat);

    /**
     * 404 405页面查询为你推荐商品
     * @author zhangdongdong
     */
    List<GoodsResp> getGoodsErrorPage();
	

}
