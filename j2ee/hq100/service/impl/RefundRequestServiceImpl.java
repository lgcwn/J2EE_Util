/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.text.DecimalFormat;
import java.util.*;

import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.GoodsResp;
import com.up72.hq.dto.resp.MemberResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import com.up72.hq.utils.Calculation;
import com.up72.hq.utils.HqUtil;
import com.up72.hq.utils.SNCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IRefundRequestService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 退款/换货申请DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class RefundRequestServiceImpl implements  IRefundRequestService{
	
	@Autowired
	private RefundRequestMapper refundRequestMapper;
	@Autowired
	private OrderGoodsMapper orderGoodsMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private OrderRefundLogMapper orderRefundLogMapper;

	public void save(RefundRequest refundRequest){
		refundRequestMapper.insert(refundRequest);
	}

    public void update(RefundRequest refundRequest){
		refundRequestMapper.update(refundRequest);
	}
	
    public void delete(java.lang.Long id){
		refundRequestMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public RefundRequest getById(java.lang.Long id){
		return refundRequestMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<RefundRequest> getPage(Map params, PageBounds rowBounds){
        PageList list = refundRequestMapper.findPage(params, rowBounds);
		return new Page<RefundRequest>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public RefundRequest getByOrderIdGoodsId(Map map) {
		return refundRequestMapper.findByOrderIdGoodsId(map);
	}

	@Transactional(readOnly=true)
	public RefundRequest getByReturnSn(String orderRefundSn) {
		return refundRequestMapper.findByReturnSn(orderRefundSn);
	}
	@Transactional(readOnly=true)
	public Integer getMemberOrderCnt(Map map) {
		return refundRequestMapper.findUserOrderCount(map);
	}

	@Override
	public Map<String,Object> doAddOrEdit(HttpServletRequest request, RefundRequest refundRequest) {
		Map<String,Object> mess = new HashMap<String, Object>();
		MemberResp user = HqUtil.getLoginMember(request);
		//新增订单
		if(refundRequest.getId() == null ){
			refundRequest.setAddTime(Cnst.getCurTime());
			refundRequest.setReturnSn(SNCode.getTradeNo());//退换货申请单编号
			refundRequest.setGoodsStatus(-2);//退换货物状态 -2 待客服处理
			refundRequest.setCsStatus(0);//客服处理状态 0待处理
			refundRequest.setIsDelete(0);
			refundRequest.setMemberId(user.getId());
			if(refundRequest.getRefundType() == 1){
				//退款金额
				Double price =Double.parseDouble(Calculation.mul(refundRequest.getRefundAmount(),refundRequest.getGoodsCount()));
				refundRequest.setRefundAmount(price);
				refundRequest.setActualAmount(price);
			}
			refundRequestMapper.insert(refundRequest);
			//订单商品状态修改
			OrderGoods g = orderGoodsMapper.findById(refundRequest.getOrderGoodsId());
			g.setReturnsStatus(1);
			Long count =0L;
			Long recount = g.getRefundCount();
			Long refundCount =(long) refundRequest.getGoodsCount();
			if(recount != null && recount>0){
				count = recount+refundCount;
			}else{
				count = refundCount;
			}
			g.setRefundCount(count);
			orderGoodsMapper.update(g);
			mess.put("success",true);
		}else{
			RefundRequest db = refundRequestMapper.findById(refundRequest.getId());
			/*if(db.getCsStatus() == -1 ){
				db.setAddTime(Cnst.getCurTime());
				db.setReturnSn(SNCode.getTradeNo());//退换货申请单编号
				db.setGoodsStatus(-2);//退换货物状态 -2 待客服处理
				db.setCsStatus(0);//客服处理状态 0待处理
				//退款金额
				Double price = Double.parseDouble(Calculation.mul(refundRequest.getRefundAmount(),refundRequest.getGoodsCount()));
				db.setRefundAmount(price);
				db.setActualAmount(price);
				db.setIsDelete(0);
				db.setMemberId(user.getId());
				refundRequestMapper.update(db);
				//订单商品状态修改
				OrderGoods g = orderGoodsMapper.findById(refundRequest.getOrderGoodsId());
				g.setReturnsStatus(1);
				Long count =0L;
				Long recount = g.getRefundCount();
				Long refundCount = (long)refundRequest.getGoodsCount();
				if(recount != null && recount>0){
					count = recount+refundCount;
				}else{
					count = refundCount;
				}
				g.setRefundCount(count);
				orderGoodsMapper.update(g);
			}else{*/
				db.setCsStatus(0);//客服处理状态 0待处理
				db.setGoodsStatus(-2);//退换货物状态 -2 待客服处理
				db.setRefundType(refundRequest.getRefundType());
				db.setPickupAddress(refundRequest.getPickupAddress());
				db.setGoodsCount(refundRequest.getGoodsCount());
				//订单商品数量
				OrderGoods g = orderGoodsMapper.findById(refundRequest.getOrderGoodsId());
				Long count =0L;
				Long recount = g.getRefundCount();
				Long refundCount = (long)refundRequest.getGoodsCount();
				if(recount != null && recount>0){
					count = recount+refundCount;
				}else{
					count = refundCount;
				}
				g.setRefundCount(count);
				g.setReturnsStatus(1);
				orderGoodsMapper.update(g);
				//退款金额
				Double price = Double.parseDouble(Calculation.mul(refundRequest.getRefundAmount(),refundRequest.getGoodsCount()));
				db.setRefundAmount(price);
				db.setActualAmount(price);
				db.setRefundReason(refundRequest.getRefundReason());
				db.setImages(refundRequest.getImages());
				db.setIsSmallTicket(refundRequest.getIsSmallTicket());
				db.setIsTestReport(refundRequest.getIsTestReport());
				db.setPickupMethod(refundRequest.getPickupMethod());
				db.setConsignee(refundRequest.getConsignee());
				db.setMobilePhone(refundRequest.getMobilePhone());
				if(refundRequest.getPickupTime() != null){
					db.setPickupTime(refundRequest.getPickupTime());
				}
				if(refundRequest.getPickupTimeType() != null){
					db.setPickupTimeType(refundRequest.getPickupTimeType());
				}
				if(refundRequest.getAddressDetail() != null){
					db.setAddressDetail(refundRequest.getAddressDetail());
				}
				refundRequestMapper.update(db);
				mess.put("success",true);
			}
		/*}*/
		return mess;
	}
	@Transactional(readOnly=true)
	public RefundRequest getRefundRequestDetail(Long refundRequestId) {
		RefundRequest refundRequest=  refundRequestMapper.findById(refundRequestId);
		return refundRequest;
	}

	@Override
	public Boolean customerServiceProcessing(RefundRequest model) {
		try{
			RefundRequest refundRequest=getRefundRequestDetail(model.getId());
			//修改客服处理状态
			refundRequest.setCsStatus(model.getCsStatus());
			refundRequest.setCsStatusTime(new Date().getTime());
			refundRequest.setCsRetunrInfo(model.getCsRetunrInfo());
			//退款日志
			OrderRefundLog orderRefundLog = orderRefundLogMapper.findByRefundId(refundRequest.getId());
			OrderGoods orderGoods=orderGoodsMapper.findById(refundRequest.getOrderGoodsId());
			//修改订单商品状态
			if(model.getCsStatus() == Cnst.RefundRequest.CustomerServiceStatus.REJECT){//驳回
				orderGoods.setReturnsStatus(Cnst.OrderGoods.RefundStatus.REJECT);
				if(orderRefundLog!=null){
					orderRefundLog.setStatus(1);
					orderRefundLogMapper.update(orderRefundLog);
				}
				refundRequest.setGoodsStatus(Cnst.RefundRequest.GoodsStatus.CANCEL);
				refundRequest.setCancelTime(Cnst.getCurTime());
				Long refundCount = orderGoods.getRefundCount();
				Integer goodsCount=	refundRequest.getGoodsCount();
				orderGoods.setRefundCount(refundCount - goodsCount);
			}
			if(model.getCsStatus() == Cnst.RefundRequest.CustomerServiceStatus.AGREE){//客服同意
				orderGoods.setReturnsStatus(Cnst.OrderGoods.RefundStatus.AGREE);
				refundRequest.setGoodsStatus(0);
				refundRequest.setConsignee(model.getConsignee());
				refundRequest.setAddressDetail(model.getAddressDetail());
				refundRequest.setMobilePhone(model.getMobilePhone());
			}
			orderGoodsMapper.update(orderGoods);
			refundRequestMapper.update(refundRequest);
			return true;
		}catch (Exception e){
			return false;
		}

	}

}
