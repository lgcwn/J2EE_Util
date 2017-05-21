/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.*;
import com.up72.hq.dto.resp.OrderGoodsCommentResp;
import com.up72.hq.dto.resp.ProdResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.model.Order;
import com.up72.hq.service.*;

import com.up72.hq.utils.HqUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IOrderGoodsCommentService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderGoodsCommentServiceImpl implements  IOrderGoodsCommentService{
	
	@Autowired
	private OrderGoodsCommentMapper orderGoodsCommentMapper;

	@Autowired
	private OrderGoodsMapper orderGoodsMapper;
	@Autowired
	private ProdMapper prodMapper;
	@Autowired
	private OrderMapper orderMapper;
	
	public void save(OrderGoodsComment orderGoodsComment){
		orderGoodsCommentMapper.insert(orderGoodsComment);
	}

    public void update(OrderGoodsComment orderGoodsComment){
		orderGoodsCommentMapper.update(orderGoodsComment);
	}
	
    public void delete(java.lang.Long id){
		orderGoodsCommentMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public OrderGoodsComment getById(java.lang.Long id){
		return orderGoodsCommentMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<OrderGoodsComment> getPage(Map params, PageBounds rowBounds){
        PageList list = orderGoodsCommentMapper.findPage(params, rowBounds);
		return new Page<OrderGoodsComment>(list,list.getPagination());
	}

	@Override
	public Page<OrderGoodsCommentResp> getPageResp(Map params, PageBounds rowBounds) {
		PageList list = orderGoodsCommentMapper.findPageResp(params, rowBounds);
		return new Page<OrderGoodsCommentResp>(list,list.getPagination());
	}

	@Override
	public Integer getCommentNumber(String type,Long prodId) {
		Map<String,Object> maps = new HashMap<String, Object>();
		//类型 （好评 中评 差评）
		if("good".equals(type)){
			maps.put("good","good");
		}else if("middle".equals(type)){
			maps.put("middle","middle");
		}else if("wrong".equals(type)){
			maps.put("wrong","wrong");
		}
		maps.put("prodId",prodId);
		Integer sum = orderGoodsCommentMapper.findCommentNumber(maps);
		if(sum==null){
			sum=0;
		}
		return sum;
	}

	@Override
	public void saveComment(String orderGoodsId,String content,String star,HttpServletRequest request) {
		if(StringUtils.isNotEmpty(orderGoodsId)){
			Member member=HqUtil.getLoginMember(request);
			OrderGoods goods = orderGoodsMapper.findById(Long.parseLong(orderGoodsId));
			if(ObjectUtils.isEmpty(goods.getReviewStatus())){
				if(ObjectUtils.isNotEmpty(goods)){
					OrderGoodsComment model = new OrderGoodsComment();
					model.setMemberId(member.getId());
					model.setGoodsId(goods.getGoodsId());
					model.setAddTime(new Date().getTime());
					model.setOrderId(goods.getOrderId());
					model.setContent(content);
					model.setLevel(Integer.parseInt(star));
					model.setStatus(1);
					ProdResp prodResp=prodMapper.findByGoodsId(goods.getGoodsId());
					if(ObjectUtils.isNotEmpty(prodResp)){
						model.setProdId(prodResp.getId());
					}
					orderGoodsCommentMapper.insert(model);
					goods.setCommentStatus(1);
					orderGoodsMapper.update(goods);
					//订单评价状态处理
					Order dhsOrder= orderMapper.findById(goods.getOrderId());
					if(ObjectUtils.isNotEmpty(dhsOrder)){
						List<OrderGoods> list = orderGoodsMapper.findByOrderId(dhsOrder.getId());
						if(list.size()>0){
							Integer status = 0;
							for(OrderGoods orderGoods : list){
								if(ObjectUtils.isNotEmpty(orderGoods) && orderGoods.getCommentStatus() == 0){
									status = 1;
									break;
								}
							}
							if(status == 0){
								dhsOrder.setIsComment(1);
								orderMapper.update(dhsOrder);
							}
						}
					}
				}
			}else{
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("memberId",member.getId());
				params.put("orderId",goods.getOrderId());
				params.put("goodsId",goods.getGoodsId());
				PageList<OrderGoodsComment> orderGoodsCommentPageList=orderGoodsCommentMapper.findPage(params,new PageBounds(1,1));
				OrderGoodsComment orderGoodsComment=orderGoodsCommentPageList.get(0);
				if(ObjectUtils.isNotEmpty(orderGoodsComment)){
					orderGoodsComment.setContent(content);
					orderGoodsCommentMapper.update(orderGoodsComment);
				}
			}

		}
	}

	@Override
	public Map<String, Object> getProdComment(HttpServletRequest request,Long prodId,Long goodsId) {
		Map<String, Object> returnParams = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		if(prodId != null){
			params.put("prodId",prodId);
		}
		if(goodsId != null){
			params.put("goodsId",goodsId);
		}
		params.put("status",1);
		String type = request.getParameter("type");
		PageBounds pageBounds = $getPageBounds(request,10);
		pageBounds.setOrders(com.up72.framework.util.page.Order.formString("ID.DESC"));
		//类型 （好评 中评 差评）
		if(StringUtils.isNotEmpty(type)){
			if("good".equals(type)){
				params.put("good","good");
			}else if("middle".equals(type)){
				params.put("middle","middle");
			}else if("wrong".equals(type)){
				params.put("wrong","wrong");
			}
		}
		PageList<OrderGoodsCommentResp> dhsOrderGoodsComments = orderGoodsCommentMapper.findPageResp(params, pageBounds); // 查询page
		returnParams.put("params",params);
		returnParams.put("prodCommentList",dhsOrderGoodsComments);
		returnParams.put("pagination",dhsOrderGoodsComments.getPagination());
		returnParams.put("thisPageNumber",dhsOrderGoodsComments.getPagination().getPageNumber());
		returnParams.put("thisPageCount",dhsOrderGoodsComments.getPagination().getTotalPages());
		returnParams.put("totalCount",dhsOrderGoodsComments.getPagination().getTotalCount());
		return returnParams;
	}
	/** 获取分页信息 */
	private static PageBounds $getPageBounds(ServletRequest request, int... pageSize) {
		int size = 15;
		if (pageSize.length > 0 && pageSize[0] > 0) {
			size = pageSize[0];
		}
		int pageNumber = $getParam(request, "pageNumber", 1);
		if (pageNumber < 1) pageNumber = 1;
		PageBounds pageBounds = new PageBounds(pageNumber, size);
		return pageBounds;
	}
	private static final int $getParam(ServletRequest request, String name,
									   int defval) {
		String param = request.getParameter(name);
		int value = defval;
		if (param != null) {
			try {
				value = Integer.parseInt(param);
			} catch (NumberFormatException ignore) {
			}
		}
		return value;
	}
	

}
