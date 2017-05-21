/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.OrderGoodsResp;
import com.up72.hq.model.OrderGoods;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * 订单商品接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IOrderGoodsService {

    void save(OrderGoods dhsOrderGoods);

    void update(OrderGoods dhsOrderGoods);

    void delete(Long id);

    OrderGoods getById(Long id);

    OrderGoodsResp getRespById(Long id);

    Page<OrderGoodsResp> getPage(Map params, PageBounds rowBounds);
    /**
     * 根据用户查询评价商品
     *
     * @author haiyi
     * @param request
     * @param pageBounds
     * @return
     */
    Map<String,Object> getCommentGoods(HttpServletRequest request, PageBounds pageBounds);

    List<OrderGoods> getOrderGoodsList(OrderGoods dhsOrderGoods);

}
