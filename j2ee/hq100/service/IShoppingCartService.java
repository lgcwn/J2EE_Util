/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.model.ShoppingCart;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 购物车接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface IShoppingCartService {
	
	void save(ShoppingCart shoppingCart);

    void update(ShoppingCart shoppingCart);
	
	void delete(java.lang.Long id);
	
    ShoppingCart getById(java.lang.Long id);

    Page<ShoppingCart> getPage(Map params, PageBounds rowBounds);


    /**
     *
     * 添加购物车
     *
     * 包含redis
     *
     * @author liguuicheng
     * @param request
     * @param response
     * @param goodsId
     * @return
     */
    String addShoppingCart(HttpServletRequest request, HttpServletResponse response, Long goodsId,Integer count);


    /**
     *
     * 新增购物车
     *
     * 添加到数据库
     *
     * @param goodsId
     * @param memberId
     * @return
     */
    String addShoppingCart(Long goodsId,Long memberId,Integer count);
    /**
     *
     * 批量新增购物车
     * 添加到数据库
     * @param goodsIds
     * @param count
     * @return
     */
    String addShoppingCartList(HttpServletRequest request, HttpServletResponse response, String goodsIds,Integer count);


    /**
     *
     * 本人购物车商品数量
     *
     * @author liguuicheng
     * @param request
     * @param response
     * @return
     */
    Integer countShoppingCart(HttpServletRequest request, HttpServletResponse response);


    /**
     *
     * 查看我的购物车
     *
     * @author liguuicheng
     * @param request
     * @param response
     * @return
     */
    Map<String,Object> showShoppingCart(HttpServletRequest request, HttpServletResponse response);


    /**
     *
     *
     * 删除购物车
     *
     * @author liguuicheng
     *
     * @param shoppingCartIds   购物车id   多个以,隔开
     * @return
     */
    public Boolean deleteShoppingCart(String shoppingCartIds,HttpServletRequest request,HttpServletResponse response);


    /**
     *
     *
     * 将购物车移至我的收藏
     *
     * @author liguuicheng
     * @param shoppingCartIds   购物车id   多个以,隔开
     * @return
     */
    String moveShoppingCartToMyLike(String shoppingCartIds,HttpServletRequest request);


    /**
     *
     * 购物车数量增减
     *
     * @author liguuicheng
     * @param shoppingCartIdIdC
     * @param type  1 增  2 减
     * @return
     */
    public String shoppingCartChangeCount(String shoppingCartIdIdC, Integer type,HttpServletRequest request,HttpServletResponse response);



}
