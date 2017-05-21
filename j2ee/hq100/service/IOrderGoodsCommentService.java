/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.OrderGoodsCommentResp;
import com.up72.hq.model.OrderGoodsComment;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;


/**
 * 接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IOrderGoodsCommentService {
	
	void save(OrderGoodsComment orderGoodsComment);

    void update(OrderGoodsComment orderGoodsComment);
	
	void delete(java.lang.Long id);
	
    OrderGoodsComment getById(java.lang.Long id);

    Page<OrderGoodsComment> getPage(Map params, PageBounds rowBounds);

    Page<OrderGoodsCommentResp> getPageResp(Map params, PageBounds rowBounds);

    /**
     * 获取产品评论数量
     *
     * @author haiyi
     * @param  type
     * @param  prodId
     * @return
     */
    Integer getCommentNumber(String type,Long prodId);
    /**
     * 保存订单商品评论
     *
     * @author haiyi
     * @param request
     * @return
     */
    void saveComment(String orderGoodsId,String content,String star,HttpServletRequest request);
    /**
     * 获取产品评论
     *
     * @author haiyi
     * @param request
     * @return
     */
    Map<String,Object> getProdComment(HttpServletRequest request,Long prodId,Long  goodsId);
	

}
