/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.OrderGoodsResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IOrderGoodsService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单商品DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderGoodsServiceImpl implements  IOrderGoodsService{

	@Autowired
	private OrderGoodsMapper orderGoodsMapper;

	public void save(OrderGoods dhsOrderGoods){
		orderGoodsMapper.insert(dhsOrderGoods);
	}

	public void update(OrderGoods dhsOrderGoods){
		orderGoodsMapper.update(dhsOrderGoods);
	}

	public void delete(Long id){
		orderGoodsMapper.delete(id);
	}

	@Transactional(readOnly=true)
	public OrderGoods getById(Long id){
		return orderGoodsMapper.findById(id);
	}

	@Transactional(readOnly=true)
	public Page<OrderGoodsResp> getPage(Map params, PageBounds rowBounds){
		PageList list = orderGoodsMapper.findPage(params, rowBounds);
		return new Page<OrderGoodsResp>(list,list.getPagination());
	}

	@Override
	public OrderGoodsResp getRespById(Long id) {
		return orderGoodsMapper.findRespById(id);
	}

	@Override
	public Map<String,Object> getCommentGoods(HttpServletRequest request,PageBounds pageBounds){
		Map<String,Object> params = new HashMap<String,Object>();
		Map map = new HashMap<>();
		Member member = (Member) request.getSession().getAttribute(Cnst.Member.LOGIN_MEMBER);
		if(ObjectUtils.isNotEmpty(member)){
			String type = request.getParameter("type");
			if(StringUtils.isNotEmpty(type)){
				if("0".equals(type)){
					map.put("commentStatus",0);
				}
				if("-1".equals(type)){
					map.put("commentAll","commentAll");
				}
			}
			params.put("type",type);
			map.put("memberId",member.getId());
			PageList list = orderGoodsMapper.findCommentResp(map, pageBounds);
			Page<OrderGoods> pages = new Page<OrderGoods>(list,list.getPagination());
			request.setAttribute("list",pages.getResult());
			request.setAttribute("params",params);
			request.setAttribute("pagination",pages.getPagination());
			request.setAttribute("thisPageNumber",list.getPagination().getPageNumber());
			request.setAttribute("thisPageCount",list.getPagination().getTotalPages());
		}
		return params;
	}

	@Transactional(readOnly=true)
	public List<OrderGoods> getOrderGoodsList(OrderGoods dhsOrderGoods){
		return orderGoodsMapper.findOrderGoodsList(dhsOrderGoods);
	}

}
