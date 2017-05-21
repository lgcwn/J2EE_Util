/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.up72.component.alipay.AlipayConstant;
import com.up72.component.alipay.AlipayDirectHelper;
import com.up72.component.alipay.AlipayHelperSingleton;
import com.up72.component.alipay.exception.AlipayExcetion;
import com.up72.component.pay.common.PayInfoUtil;
import com.up72.component.wxpay.WxPayUtils;
import com.up72.component.wxpay.protocol.RefundResData;
import com.up72.component.wxpay.protocol.UnifiedOrderDto;
import com.up72.component.wxpay.service.UnifiedOrderService;
import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.Page;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.*;
import com.up72.hq.dto.req.ToSettlement;
import com.up72.hq.dto.resp.*;
import com.up72.hq.model.*;
import com.up72.hq.service.IOrderService;
import com.up72.hq.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 订单DAO实现
 *
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class OrderServiceImpl implements  IOrderService{

	private static final Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private MemberMessageMapper memberMessageMapper;
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderGoodsMapper orderGoodsMapper;
	@Autowired
	private OrderShippingInfoMapper orderShippingInfoMapper;
	@Autowired
	private OrderLogMapper orderLogMapper;
	@Autowired
	private MemberAddressMapper memberAddressMapper;
	@Autowired
	private MemberAddressServiceImpl memberAddressService;
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	@Autowired
	private OrderPayLogMapper orderPayLogMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private ProdMapper prodMapper;
	@Autowired
	private PayListMapper payListMapper;
	@Autowired
	private OrderRefundLogMapper orderRefundLogMapper;
	@Autowired
	private ScoreListMapper 	scoreListMapper;
	@Autowired
	private OrderCrowdServiceImpl orderCrowdServiceImpl;

	public void save(Order order){
		orderMapper.insert(order);
	}

	@Override
	public void saveOrderCrowd(Order order, OrderCrowd orderCrowd) {
		save(order);
		orderCrowd.setOrderId(order.getId());
		orderCrowd.setGoodsPrice(order.getActualPayments());
		orderCrowd.setGoodsCount(1);
		orderCrowd.setCreateDate(Cnst.getCurTime());
		orderCrowd.setGoodsSn(order.getSn());
		orderCrowdServiceImpl.save(orderCrowd);
	}
	@Override
	public GoodsResp getXnOrJfGoodsResp(Long goodsId, Integer count) {
		GoodsResp goodsResp=goodsMapper.findRespById(goodsId);
		goodsResp.setTotalAmount(String.valueOf(goodsResp.getPriceInt() * count));
		goodsResp.setCount(count);
		return  goodsResp;
	}

	public void update(Order order){
		orderMapper.update(order);
	}

	public void delete(Long id){
		Order order=orderMapper.findById(id);

		order.setIsDelete(1);

		orderMapper.update(order);
	}

	@Transactional(readOnly=true)
	public OrderResp getById(Long id){
		return orderMapper.findById(id);
	}

	@Override
	public Order getId(Long id) {
		return orderMapper.findId(id);
	}

	@Override
	public OrderResp getByIdCrowd(Long id) {
		return orderMapper.findByIdCrowd(id);
	}

	@Transactional(readOnly=true)
	public Page<Order> getPage(Map params, PageBounds rowBounds){
		PageList list = orderMapper.findPage(params, rowBounds);
		return new Page<Order>(list,list.getPagination());
	}

	@Override
	public Page<OrderResp> getByCrowdPage(Map params, PageBounds rowBounds) {
		PageList list = orderMapper.findByCrowdPage(params, rowBounds);
		return new Page<OrderResp>(list,list.getPagination());
	}

	@Transactional(readOnly=true)
	public Page<OrderResp> getPageResp(Map params, PageBounds rowBounds) {
		PageList list = orderMapper.findPageResp(params, rowBounds);
		Page<OrderResp> page = new Page<OrderResp>(list,list.getPagination());
		return page;
	}


	@Override
	public Page<OrderResp> orderCenterMyOrder(Map params,PageBounds pageBounds) {
		Long beginTime=0L;
		Long endTime=0L;
		if(params.get("time") == null){
			params.put("time",1);
		}
		if(params.get("orderStatus")!=null && params.get("orderStatus").equals("3")){
			params.put("isComment",0);
		}


		Integer i=Integer.parseInt(params.get("time").toString());
		DateUtils date=new DateUtils();
		Integer year=DateUtils.getCurrYear();

		switch (i){
			case 1://近三个月
				beginTime=DateUtils.dateToLong(new Date(),-90);
				endTime=Cnst.getCurTime();
				break;
			case 2://今年内
				beginTime=DateUtils.getCurrYearFirst().getTime();
				endTime=Cnst.getCurTime();
				break;
			case 3://2015年订单
				beginTime=DateUtils.getYearFirst(year-1).getTime();
				endTime=DateUtils.getYearLast(year-1).getTime();
				break;
			case 4://2014年订单
				beginTime=DateUtils.getYearFirst(year-2).getTime();
				endTime=DateUtils.getYearLast(year-2).getTime();
				break;
			case 5://2013年订单
				beginTime=DateUtils.getYearFirst(year-3).getTime();
				endTime=DateUtils.getYearLast(year-3).getTime();
				break;
			case 6://2013年以前订单
				beginTime=DateUtils.getYearFirst(year-4-10).getTime();
				endTime=DateUtils.getYearLast(year-4).getTime();
				break;
		}
		DateUtils d=new DateUtils();
		params.put("beginTime",beginTime);
		params.put("endTime",endTime);
		boolean isStatus=false;
		if(params.get("orderStatus") != null && params.get("orderStatus").equals("1")){
			params.put("orderStatusReceipt","1");
			params.remove("orderStatus");
			isStatus=true;
		}
		PageList list=	orderMapper.findPageResp(params, pageBounds);

		if(isStatus){
			params.put("orderStatus","1");
		}
		Page<OrderResp> page = new Page<OrderResp>(list,list.getPagination());

		List<OrderResp> orders=page.getResult();

		for (OrderResp order:orders){//订单商品
			List<OrderGoods> goodses=	orderGoodsMapper.findByOrderId(order.getId());
			order.setGoodses(goodses);
		}
		page.setResult(orders);
		return page;
	}


	@Override
	public Order orderDetail(Map map) {
		try{
			String code=map.get("code").toString();

			Long id= CodeEncryption.express(code);

			//订单信息
			Order order=orderMapper.findById(id);

			//订单商品
			order.setOrderGoodses(orderGoodsMapper.findByOrderId(order.getId()));

			return order;
		}catch (Exception e){
			return null;

		}
	}

	@Override
	public Order newOrder() {
		Order order = new Order();
		order.setOrderStatus(1);
		order.setIsExpire(0);
		order.setIsDelete(0);
		List<Order> list = orderMapper.findnewOrder(order);
		if(list.isEmpty()){
			return null;
		}else{
			Map map=new HashMap();
			map.put("code",list.get(0).getIdCipher());
			Order order1 = orderDetail(map);
			return order1;
		}

	}

	@Override
	public List<OrderResp> getOrderLists(Order order){
		return orderMapper.findOrderLists(order);
	}

	@Override
	public List<OrderResp> getOrderStatusSize(Order order){
		return orderMapper.findOrderStatusSize(order);
	}


	@Override
	public String cancelOrder(Long memberId, String orderIdEncr) {

		String f="success";
		Long orderId=CodeEncryption.express(orderIdEncr);
		Order order=orderMapper.findById(orderId);
		if(order != null){//订单是否存在
			if(memberId.longValue() == order.getMemberId().longValue() ){//该订单是否为当前登录用户的id
				Integer status=order.getOrderStatus();
				if(status == Cnst.Order.Status.WAIT_PAY){//待付款状态取消订单
					List<OrderGoods> orderGoodsList=orderGoodsMapper.findByOrderId(order.getId());
					for(OrderGoods orderGoods:orderGoodsList){
						Goods goods=goodsMapper.findById(orderGoods.getGoodsId());
						goods.setStock(goods.getStock() + orderGoods.getGoodsCount());
						goodsMapper.update(goods);
						Prod prod = prodMapper.findByGoodsId(orderGoods.getGoodsId());
						if(ObjectUtils.isEmpty(prod.getStock())){
							prod.setStock(0);
						}
						prod.setStock(prod.getStock() + orderGoods.getGoodsCount());
						prodMapper.update(prod);
					}
					Long currTime=Cnst.getCurTime();
					order.setCancelTime(currTime);
					order.setModifyDate(currTime);
					order.setOrderStatus(4);
					orderOperationLog(order.getId(),"用户取消订单(订单编号:"+order.getSn()+")",order.getId());
					orderMapper.update(order);
				}else{
					f="status";
				}
			}else{//不是当前登录用户的订单  返回逻辑码
				f="user";
			}
		}else{//订单id错误或不存在该订单
			f="order";
		}
		return f;
	}

	@Override
	public String confirmReceipt(Long memberId, String orderIdEncr) {
		String f="success";
		Long orderId=CodeEncryption.express(orderIdEncr);
		Order order=orderMapper.findById(orderId);
		if(order != null){//订单是否存在
			if(memberId.longValue() == order.getMemberId().longValue() ){//该订单是否为当前登录用户的id
				Integer status=order.getOrderStatus();
				if(status == Cnst.Order.Status.SENDED){//待自提状态 确认收货
					Long currTime=Cnst.getCurTime();
					order.setOrderStatus(Cnst.Order.Status.SUCCESS);
					order.setReceivingTime(currTime);
					order.setCompleteTime(currTime);
					order.setModifyDate(currTime);
					orderOperationLog(order.getId(),"用户确认收货【订单编号:"+order.getSn()+"】",order.getId());
					orderMapper.update(order);
				}else{
					f="status";
				}
			}else{//不是当前登录用户的订单  返回逻辑码
				f="user";
			}
		}else{//订单id错误或不存在该订单
			f="order";
		}
		return f;
	}

	@Override
	public List<Order> getExpiredOrder(Long now) {
		Map<String,Object> params = new HashMap<String,Object>();
		//搜索条件
		params.put("time",now);
		params.put("paramOrderStatus",Cnst.Order.Status.WAIT_PAY);
		params.put("paramIsExpire",Cnst.NO);
		params.put("paramIsDelete",Cnst.NO);
		params.put("paramSellerIsDelete",Cnst.NO);
		return orderMapper.findExpiredOrder(params);
	}

	@Override
	public void updateStock(Order order) {
		//查找订单下的所有商品
		List<OrderGoods> orderGoodsList = orderGoodsMapper.findByOrderId(order.getId());
		if(orderGoodsList != null && orderGoodsList.size() > 0){
			OrderGoods orderGoods = null;
			//对每个订单商品的对应仓库、商品总库存都进行添加
			for (int i = 0; i < orderGoodsList.size(); i++) {
				orderGoods = orderGoodsList.get(i);
				//将商品对应库存增加
				goodsMapper.updateStock(orderGoods.getGoodsCount(),orderGoods.getGoodsId(),new Date());
			}
		}
	}

	@Override
	public void updateExpireOrder(Long now) {
		Map<String,Object> params = new HashMap<String,Object>();
		//修改后的值
		params.put("orderStatus", Cnst.Order.Status.CANCEL);
		params.put("time", now);
		params.put("isExpire", Cnst.YES);
		params.put("remark", "订单逾期未支付，系统自动取消");
		//下面是搜索条件
		params.put("paramOrderStatus", Cnst.Order.Status.WAIT_PAY);
		params.put("paramIsExpire", Cnst.NO);
		params.put("paramIsDelete", Cnst.NO);
		params.put("paramSellerIsDelete", Cnst.NO);
		orderMapper.updateExpireOrder(params);
	}


	private void orderOperationLog(Long orderId,String info,Long operationUserId){
		OrderLog orderLog=new OrderLog();
		orderLog.setHandleDate(Cnst.getCurTime());
		orderLog.setHandleInfo(info);
		orderLog.setOrderId(orderId);
		orderLog.setHandleUser(operationUserId);
		orderLogMapper.insert(orderLog);
	}

	@Transactional
	public Map previewOrder(Long memberId,String code, String region) {

		Map result=new HashMap<>();

		List<ToSettlement> list= OrderUtil.getToSettlementParam(code);

		List<GoodsResp> goodsResps=new ArrayList<GoodsResp>();

		DecimalFormat df   = new DecimalFormat("######0.00");
		Double totalAmount=0.00;
		for(ToSettlement toSettlement:list){
			GoodsResp goodsResp=goodsMapper.findRespById(toSettlement.getGoodsId());
			Double goodsPrice=Double.parseDouble(Calculation.mul(goodsResp.getPrice(), toSettlement.getCount().doubleValue()));
			goodsResp.setCount(toSettlement.getCount());
			goodsResp.setTotalAmount(df.format(goodsPrice));
			goodsResps.add(goodsResp);
			totalAmount=Double.parseDouble(Calculation.add(totalAmount, goodsPrice));
		}
		//收货地址选择
		Map param=new HashMap<>();
		param.put("memberId",memberId);
		param.put("isDel",0);
		result.put("goods",goodsResps);
		result.put("address",memberAddressService.getMemberAddress(memberId));
		result.put("code",code);
		result.put("freight","0.00");
		result.put("totalAmountBefor",df.format(totalAmount));
		result.put("totalAmount",Calculation.add(totalAmount, 0.00));//运费
		result.put("time",Cnst.getCurTime());
		return result;
	}


	@Transactional
	public Map<String,String> placeAnOrder(Order order,String code,String addressId,String invoiceData) {

		String f="success";
		List<ToSettlement> list=OrderUtil.getToSettlementParam(code);
		MemberAddressResp memberAddress=memberAddressMapper.findById(Long.valueOf(addressId));
		//地址信息
		Map map=null;
			//金额计算 删减商品 库存
			DecimalFormat df   = new DecimalFormat("######0.00");
			Double totalAmount=0.00;

			for(ToSettlement toSettlement:list){
				Goods goodsResp=goodsMapper.findById(toSettlement.getGoodsId());

				Double goodsPrice=Double.parseDouble(Calculation.mul(goodsResp.getPrice(),toSettlement.getCount().doubleValue()));

				totalAmount=Double.parseDouble(Calculation.add(totalAmount,goodsPrice));

				goodsResp.setStock(goodsResp.getStock() - toSettlement.getCount());//减库存

				goodsMapper.update(goodsResp);
			}

			Double transportCost=0.00;//运费

			Double payableGoodsAmount=Double.parseDouble(df.format(totalAmount));//商品总金额
			order.setOrderStatus(0);
			order.setType(Cnst.Order.Type.MS);
			order.setIsDelete(0);
			order.setIsExpire(0);
			order.setIsUseScore(0);//是否使用积分(0否;1是)
			order.setScoreCount(0);
			order.setTradeNo(SNCode.getTradeNo());
			order.setIsInvoice(0);//是否开发票(0否;1是)

			order.setIsComment(0);
			order.setAddTime(Cnst.getCurTime());
			order.setTransportCost(transportCost);
			order.setIsPhoneOrder(0);

			order.setActualPayments(Double.parseDouble(Calculation.add(payableGoodsAmount, transportCost))); //实付金额:商品总金额-优惠金额折扣-积分抵扣 + 运费
			order.setPayableAmount(Double.parseDouble(Calculation.add(payableGoodsAmount, transportCost)));//应付金额:商品总金额 + 运费
			order.setPayableGoodsAmount(payableGoodsAmount);//商品总价格
			order.setBuyerExtendedReceived(0);
			order.setSn(SNCode.getTradeNo());

			//收货地址
			order.setConsignee(memberAddress.getMemberName());
			order.setAddressDetail(memberAddress.getFullName() + memberAddress.getDetailAddress());
			order.setZipCode(memberAddress.getPostalCode());
			order.setMobilePhone(memberAddress.getPhone());
			order.setEmail(memberAddress.getEmail());
			order.setFixedPhone(memberAddress.getPhone());
			orderMapper.insert(order);

			for(ToSettlement toSett:list){
				GoodsResp goodsResp=goodsMapper.findRespById(toSett.getGoodsId());
				OrderGoods orderGoods=new OrderGoods();
				orderGoods.setGoodsId(toSett.getGoodsId());
				orderGoods.setCreateDate(Cnst.getCurTime());
				orderGoods.setGoodsImg(goodsResp.getMianImgStr());
				orderGoods.setOrderId(order.getId());
				orderGoods.setGoodsSn(goodsResp.getSn());
				orderGoods.setGoodsPrice(goodsResp.getPrice());
				orderGoods.setGoodsCount(toSett.getCount());
				orderGoods.setGoodsName(goodsResp.getFullName());
				orderGoods.setCommentStatus(0);
				orderGoods.setReturnsStatus(0);
				orderGoods.setIsRefund(0);
				orderGoods.setIsExchange(0);
				orderGoodsMapper.insert(orderGoods);
			}
			//  逻辑删除购物车商品
			for(ToSettlement toSettlement:list){
				if(toSettlement.getCartId() != null){
					ShoppingCart dhsShoppingCat=	shoppingCartMapper.findById(toSettlement.getCartId());
					dhsShoppingCat.setStatus(3);
					shoppingCartMapper.update(dhsShoppingCat);
				}
			}

		//用户消息
		MemberMessage memberMessage=new MemberMessage();
		memberMessage.setTitle("积分商品订单提交成功");
		memberMessage.setContent("亲爱的会员：您的订单于" + Cnst.getFormatTime(order.getAddTime()) + "提交成功，请在24小时内支付！");
		memberMessage.setAddTime(Cnst.getCurTime());
		memberMessage.setStatus(0);
		memberMessage.setHqMemberId(order.getMemberId());
		memberMessage.setIsDel(0);
		memberMessage.setType(2);
		memberMessageMapper.insert(memberMessage);

		Map<String,String> result=new HashMap<String,String>();
		result.put("status",f);
		if(f.equals("success"))
			result.put("orderId",CodeEncryption.encryption(order.getId()));

		return result;
	}

	public String saveXnOrJfOrder(Long memberId,Long goodsId,Integer totalAmount,Integer count,
								String addressId,Integer type,String remark,HttpServletRequest request) {
		//金额计算 删减商品 库存
		Member member=memberMapper.findById(memberId);
		MemberAddressResp memberAddress=memberAddressMapper.findById(Long.valueOf(addressId));
		Goods goodsResp=goodsMapper.findById(goodsId);
		int stock=goodsResp.getStock() - count;
		if(stock>0){
			goodsResp.setStock(stock);//减库存
		}else{
			goodsResp.setStock(0);//减库存
		}
		goodsMapper.update(goodsResp);
		Double transportCost=0.00;//运费
		OrderResp order=new OrderResp();
		order.setMemberId(member.getId());
		order.setOrderStatus(Cnst.Order.Status.WAIT_SEND);
		order.setType(type);
		order.setIsDelete(0);
		order.setIsExpire(0);
		order.setRemark(remark);
		order.setIsUseScore(1);//是否使用积分(0否;1是)
		order.setScoreCount(0);
		order.setTradeNo(SNCode.getTradeNo());
		order.setIsInvoice(0);//是否开发票(0否;1是)
		order.setIsComment(0);
		order.setIsPhoneOrder(0);
		order.setAddTime(Cnst.getCurTime());
		order.setTransportCost(transportCost);
		order.setActualPayments(Double.valueOf(totalAmount)); //实付金额:商品总金额-优惠金额折扣-积分抵扣 + 运费
		order.setPayableAmount(Double.valueOf(totalAmount));//应付金额:商品总金额 + 运费
		order.setPayableGoodsAmount(Double.valueOf(totalAmount));//商品总价格
		order.setBuyerExtendedReceived(0);
		order.setSn(SNCode.getTradeNo());
		//收货地址
		order.setConsignee(memberAddress.getMemberName());
		order.setAddressDetail(memberAddress.getFullName() + memberAddress.getDetailAddress());
		order.setZipCode(memberAddress.getPostalCode());
		order.setMobilePhone(memberAddress.getPhone());
		order.setEmail(memberAddress.getEmail());
		order.setFixedPhone(memberAddress.getPhone());
		orderMapper.insert(order);

		OrderGoods orderGoods=new OrderGoods();
		orderGoods.setGoodsId(goodsId);
		orderGoods.setCreateDate(Cnst.getCurTime());
		orderGoods.setGoodsImg(goodsResp.getFirstImg());
		orderGoods.setOrderId(order.getId());
		orderGoods.setGoodsSn(goodsResp.getSn());
		orderGoods.setGoodsPrice(goodsResp.getPrice());
		orderGoods.setGoodsCount(count);
		orderGoods.setGoodsName(goodsResp.getFullName());
		orderGoods.setCommentStatus(0);
		orderGoods.setReturnsStatus(0);
		orderGoods.setIsRefund(0);
		orderGoods.setIsExchange(0);
		orderGoodsMapper.insert(orderGoods);

		//添加积分消费记录
		String remarkStr="";
		//用户消息
		MemberMessage memberMessage=new MemberMessage();
		if(type==Cnst.Order.Type.XN && (member.getTb() - totalAmount)>=0){
			member.setTb(member.getTb() - totalAmount);
			remarkStr="虚拟商品兑换【订单(" + order.getSn() + ")】，兑换" + totalAmount+"T币";
			memberMessage.setTitle("商城虚拟商品兑换消费T币" + totalAmount);
			memberMessage.setContent("亲爱的会员：您于" + order.getPayTimeStr() + "使用" + totalAmount + "T币");

		}else if (type==Cnst.Order.Type.JF && (member.getScore() - totalAmount)>=0){
			member.setScore(member.getScore() - totalAmount);
			remarkStr="积分商品兑换【订单(" + order.getSn() + ")】，兑换" + totalAmount+"积分";
			//用户消息
			memberMessage.setTitle("商城积分商品兑换消费积分"+totalAmount);
			memberMessage.setContent("亲爱的会员：您于" + order.getPayTimeStr() + "使用" + totalAmount +"积分");
		}
		memberMessage.setAddTime(Cnst.getCurTime());
		memberMessage.setStatus(0);
		memberMessage.setHqMemberId(member.getId());
		memberMessage.setIsDel(0);
		memberMessage.setType(2);
		memberMessageMapper.insert(memberMessage);

		/**更新用户积分或T币*/
		memberMapper.update(member);
		request.getSession().setAttribute(Cnst.Member.LOGIN_MEMBER, member);

		ScoreList scoreList=new ScoreList();
		scoreList.setAddTime(Cnst.getCurTime());
		scoreList.setContentId(order.getId());
		scoreList.setMemberId(member.getId());
		scoreList.setScore(totalAmount);
		scoreList.setSourceType(0);
		scoreList.setIsOver(0);
		scoreList.setTbOrJf(type==Cnst.Order.Type.XN?1:2);
		scoreList.setRemark(remarkStr);
		scoreList.setOverTime(Cnst.getCurTime() + Cnst.Score.SCORE_OVER_TIME);
		scoreList.setType(2);
		scoreListMapper.insert(scoreList);
		return order.getIdCipher();
	}

	@Override
	public String paySuccess(String orderSn,String tradeNo,Integer channel,HttpServletRequest request) {
		log.warn("【免税商品订单支付】pay success: orderSn:"+orderSn+" tradeNo:"+tradeNo+" channel: "+channel);
		OrderResp order=orderMapper.findBySn(orderSn);
		if(order != null ){
			//获取order下的所有商品
			List<OrderGoods> orderGoodses = order.getGoodses();
			int score=0;
			for(OrderGoods orderGood : orderGoodses){
				score+=goodsMapper.findById(orderGood.getGoodsId()).getGivePoints();
			}


			order.setOrderStatus(Cnst.Order.Status.WAIT_SEND);
			order.setPaymentDate(Cnst.getCurTime());
			order.setPayTime(Cnst.getCurTime());
			order.setPayChannel(channel);
			order.setTradeNo(tradeNo);
			double money=order.getActualPayments();
			MemberResp member= memberMapper.findById(order.getMemberId());
			int userScore= member.getScore()==null?0: member.getScore();
			member.setScore( userScore + score );  //  返还订单相应积分
			ScoreList scoreList=new ScoreList();
			scoreList.setAddTime(Cnst.getCurTime());
			scoreList.setContentId(order.getId());
			scoreList.setMemberId(member.getId());
			scoreList.setScore(score);
			scoreList.setSourceType(0);
			scoreList.setIsOver(0);
			scoreList.setOverTime(Cnst.getCurTime() + Cnst.Score.SCORE_OVER_TIME);
			scoreList.setType(1);
			scoreList.setRemark("免税商品【订单(" + order.getSn() + ")】，获得" + score +"积分");
			scoreList.setTbOrJf(2);
			scoreListMapper.insert(scoreList);
			memberMapper.update(member);
			HqUtil.setSessionByMember(request,member);

			orderMapper.update(order);
			//订单支付记录
			OrderPayLog orderPayLog=new OrderPayLog();
			orderPayLog.setOrderId(order.getId());

			orderPayLog.setMemberId(order.getMemberId());

			orderPayLog.setPayMoney(order.getActualPayments());

			orderPayLog.setPayChannel(channel);

			orderPayLog.setPayCardType(0);

			orderPayLog.setPaySn(tradeNo);

			orderPayLog.setPayTime(Cnst.getCurTime());

			orderPayLog.setContent("订单编号:" +order.getSn()+",交易号:"+order.getTradeNo()+",支付金额:"+order.getActualPayments());

			orderPayLog.setScore((long)score);

			orderPayLogMapper.insert(orderPayLog);

			PayList payList=new PayList();

			payList.setAddDate(Cnst.getCurTime());

			payList.setBillType(0);

			payList.setMemberId(order.getMemberId());

			Integer type=0;

			if(channel == Cnst.PayMethod.ALIPAY) //支付宝
				type=2;
			if(channel == Cnst.PayMethod.UNIONPAY) //银联
				type=4;
			if(channel == Cnst.PayMethod.WEIXIN) //微信
				type=3;
			if(channel == 4) //余额
				type=1;

			payList.setPayType(type);

			payList.setCardType(0);

			payList.setMoney(order.getActualPayments());

			payList.setReMark("订单编号:"+order.getSn()+",支付金额:"+order.getActualPayments());

			payList.setStatus(0);

			payList.setSn(SNCode.getTradeNo());

			payListMapper.insert(payList);
			orderMapper.update(order);

			//用户消息
			MemberMessage memberMessage=new MemberMessage();
			memberMessage.setTitle("商城购买积分商品收入积分" + score);
			memberMessage.setContent("亲爱的会员：您于" + order.getPayTimeStr() + "到账" + score + "积分");
			memberMessage.setAddTime(Cnst.getCurTime());
			memberMessage.setStatus(0);
			memberMessage.setHqMemberId(member.getId());
			memberMessage.setIsDel(0);
			memberMessage.setType(2);
			memberMessageMapper.insert(memberMessage);

			//用户消息
			memberMessage=new MemberMessage();
			memberMessage.setTitle("积分商品订单支付成功");
			memberMessage.setContent("亲爱的会员：您的订单于" + order.getPayTimeStr() + "支付成功，我们将尽快为您安排发货！");
			memberMessage.setAddTime(Cnst.getCurTime());
			memberMessage.setStatus(0);
			memberMessage.setHqMemberId(order.getMemberId());
			memberMessage.setIsDel(0);
			memberMessage.setType(2);
			memberMessageMapper.insert(memberMessage);

		}

		return "success";
	}

	public OrderStatusCountResp getOrderStatusCount(Long memberId,Integer type) {
		OrderStatusCountResp orderStatusCountResp=new OrderStatusCountResp();
		Map map=new HashMap<>();
		map.put("isDelete", "0");
		map.put("type", type);
		if (ObjectUtils.isNotEmpty(type)){
			map.put("memberId",memberId);
		}
		List<OrderResp> orderResps=orderMapper.getOrderStatusCount(map);
		for(OrderResp orderResp:orderResps){
			if(orderResp.getOrderStatus() == 0)
				orderStatusCountResp.setToBePaidCount(orderResp.getCount());
			if(orderResp.getOrderStatus() == 1)
				orderStatusCountResp.setToBeShippedCount(orderResp.getCount());
			if(orderResp.getOrderStatus() == 2)
				orderStatusCountResp.setReceiptOfGoodsCount(orderResp.getCount());
			if(orderResp.getOrderStatus() == 3)
				orderStatusCountResp.setFinishCount(orderResp.getCount());
			if(orderResp.getOrderStatus() == 4)
				orderStatusCountResp.setCancelCount(orderResp.getCount());
			if(orderResp.getOrderStatus() == 5)
				orderStatusCountResp.setRejectionCount(orderResp.getCount());
			if(orderResp.getOrderStatus() == 6)
				orderStatusCountResp.setToBeSelfMentionedCount(orderResp.getCount());
		}
		map.put("orderStatus",0);
		map.put("addTime",0);
		//新订单
		Integer count=orderMapper.getNewOrderCount(map);
		orderStatusCountResp.setNewOrderCount(count);
		//待评价
		map.put("orderStatus",Cnst.Order.Status.SUCCESS);
		map.put("isComment",0);
		orderStatusCountResp.setToBeEvaluated(orderMapper.getOrderNotEvaluatedCount(map));
		return orderStatusCountResp;
	}


	@Override
	public String rejection(String code,Integer status) {
		Long orderId=CodeEncryption.express(code);
		Order order=orderMapper.findById(orderId);
		String f="success";
		boolean isRefund=false;
		String out_trade_no=order.getTradeNo();
		double balanceResult = 0.0;
		String returnSn=order.getTradeNo();
		if(order.getPayChannel() == 1){//支付宝
			Member member=memberMapper.findById(order.getMemberId());
			memberMapper.update(member);
			isRefund=true;
		}else if(order.getPayChannel() == 2){//银联

		}else if(order.getPayChannel() == 3){//微信
			//微信支付--退款操作
			// TODO: 16/5/31 退款金额   正式版 开放
			int total_fee = 1;
			String out_refund_no=SNCode.getTradeNo();
			returnSn = out_refund_no;
			int refund_fee = 1;
			RefundResData res = WxPayUtils.refund(out_trade_no, total_fee, out_refund_no, refund_fee);
			System.out.println("微信支付: out_trade_no:"+out_trade_no+"  out_refund_no:"+out_refund_no+"  res:"+res +"  json:"+JSONObject.toJSONString(res));
			if(res != null) {
				System.out.println("res.getReturn_code():"+res.getReturn_code()+"  res.getResult_code():"+res.getResult_code());
				if (res.getReturn_code() != null && res.getResult_code() != null && "SUCCESS".equals(res.getReturn_code()) && "SUCCESS".equals(res.getResult_code())) {
					System.out.println("----退款success:订单:" + out_trade_no + ";退款单：" + out_refund_no);
					isRefund=true;
				}else if(res.getReturn_msg() != null && !"".equals(res.getReturn_msg())){
					//数据异常
					System.out.println("----error:"+res.getReturn_msg());
				}else{
					//失败或者异常
					System.out.println("----fail 订单:"+res.getErr_code_des());
				}
			}

		}
		if(isRefund){

			PayList payList=new PayList();

			payList.setBalance(balanceResult);
			if(order.getPayChannel() == 1){
				payList.setReMark("退货或拒收:支付宝退款:"+order.getActualPayments());
			}else if(order.getPayChannel() == 2){
				payList.setReMark("退货或拒收:银联退款:"+order.getActualPayments());
			}else if(order.getPayChannel() == 3){
				payList.setReMark("退货或拒收:微信退款:"+order.getActualPayments());
			}else if(order.getPayChannel() == 4){
				payList.setReMark("退货或拒收:余额退款:"+order.getActualPayments());
			}

			payList.setBillType(2);

			payList.setStatus(0);

			payList.setAddDate(Cnst.getCurTime());

			payList.setMemberId(order.getMemberId());

			payList.setCardType(0);

			payList.setMoney(order.getActualPayments());

			payList.setSn(SNCode.getTradeNo());

			payList.setPayType(5);

			payListMapper.insert(payList);
			//退款日志
			OrderRefundLog orderRefundLog =new OrderRefundLog();
			orderRefundLog.setOrderId(order.getId());
			orderRefundLog.setAddTime(Cnst.getCurTime());
			//orderRefundLog.setChargbackRequestId(dhsRefundRequest.getId());
			orderRefundLog.setRefundSn(returnSn);
			orderRefundLog.setRefundTradeNo(out_trade_no);//有第三方存第三方返回的 没有存TradeNo
			orderRefundLog.setStatus(2);
			orderRefundLog.setRerundAmount(order.getActualPayments());
			orderRefundLogMapper.insert(orderRefundLog);
			List<OrderGoods> orderGoodsList=orderGoodsMapper.findByOrderId(order.getId());
			for(OrderGoods orderGoods:orderGoodsList){
				Goods goods=goodsMapper.findById(orderGoods.getGoodsId());
				goods.setStock(goods.getStock() + orderGoods.getGoodsCount());
				goodsMapper.update(goods);
				orderGoods.setIsRefund(1);
				orderGoodsMapper.update(orderGoods);
			}
			order.setOrderStatus(status);
			orderMapper.update(order);
		}else{
			f="fail";
		}

		return f;
	}

	@Override
	public void getCanCelOrderPage(HttpServletRequest request, PageBounds pageBounds) {
		Member member = (Member) request.getSession().getAttribute(Cnst.Member.LOGIN_MEMBER);
		if(ObjectUtils.isNotEmpty(member)){
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("memberId",member.getId());
			PageList list=	orderMapper.findCanCelOrder(params, pageBounds);
			Page page = new Page(list,list.getPagination());
			request.setAttribute("list",page.getResult());
			request.setAttribute("pagination",page.getPagination());
			request.setAttribute("thisPageNumber",page.getPagination().getPageNumber());
			request.setAttribute("thisPageCount",page.getPagination().getTotalPages());
		}
	}

	public void autoConfirmReceived(Long now) {
		Map<String,Object> params = new HashMap<String,Object>();
		//查找服务中心已收货的、订单状态为 待收货、待自提的，未删除的、并且自动收货时间比当前时间小的，将其状态改为交易成功
		params.put("time",now);//当前时间戳
		params.put("paramOrderStatus",Cnst.Order.Status.SENDED );//待收货
		params.put("paramIsDelete",Cnst.NO);//未删除
		params.put("orderStatus",Cnst.Order.Status.SUCCESS);
		orderMapper.autoConfirmReceived(params);
	}

	@Transactional(readOnly=true)
	public OrderResp getByOrderSn(String orderSn) {
		OrderResp orderResp = orderMapper.findBySn(orderSn);
		return orderResp;
	}

	@Transactional(readOnly=true)
	public Long getOrderCnt(Map params) {
		return orderMapper.findOrderCnt(params);
	}

	@Transactional(readOnly=true)
	public List<OrderResp> getByCrowd(Long memberId) {
		return orderMapper.findByCrowd(memberId);
	}

	/**
	 * 微信支付回调
	 * @param request
	 * @return
	 */
	public String wxpayInstantCallback(HttpServletRequest request,Integer type) {
		String result = "success";
		try {
			//微信支付 后台回调，处理支付后的业务
			UnifiedOrderService unifiedOrderService = new UnifiedOrderService();
			UnifiedOrderDto unifiedOrderDto = unifiedOrderService.getUnifiedOrderDtoBBC(request, null);
			if(unifiedOrderDto != null){
				String tradeNo = unifiedOrderDto.getOut_trade_no();
				if(unifiedOrderDto.getReturn_code() != null && unifiedOrderDto.getResult_code() != null && "SUCCESS".equals(unifiedOrderDto.getReturn_code()) && "SUCCESS".equals(unifiedOrderDto.getResult_code())){
					//交易成功业务处理
					//改变业务状态---以便支付完成后查询--提升体验
					paymentSuccessCallback(tradeNo,unifiedOrderDto.getTransaction_id(),type,Cnst.PayMethod.WEIXIN,request);
				}else if(unifiedOrderDto.getReturn_msg() != null && !"".equals(unifiedOrderDto.getReturn_msg())){
					//数据异常
					log.warn("----error:" + unifiedOrderDto.getReturn_msg());
				}else{
					//失败或者异常
					log.warn("----fail 订单:" + unifiedOrderDto.getErr_code_des());
				}
				//如果是扫码支付，请在此处删除生成的二维码图片
				if(unifiedOrderDto.getOut_trade_no() != null && !"".equals(unifiedOrderDto.getOut_trade_no())){
					String img_url = PayInfoUtil.instants().getValue("codeImgPath")+unifiedOrderDto.getOut_trade_no()+".png";
					String imgPath = Cnst.ROOTPATH+img_url;
					HqUtil.deleteFile(imgPath);
				}
			}else{
				//--------请求异常，或者返回数据加密异常
			}
			result = "success";//请不要修改或删除
		}  catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 众筹支付宝即时交易回调
	 * @param request
	 * @return
	 */
	public String alipayInstantCallback(HttpServletRequest request,Integer type) {
		String result = "success";
		try {
			Map requestParams = request.getParameterMap();
			AlipayDirectHelper helper = null;
			helper = (AlipayDirectHelper) AlipayHelperSingleton.getInstance().findUnSynchronizedResult(requestParams, AlipayConstant.SERVICE_DIRECT,false);
			String trade_status = helper.getTrade_status();//支付状态
			String orderSn = helper.getOut_trade_no();//唯一订单号，查询使用
			String tradeNo = helper.getTrade_no();//支付产生在交易号，该交易号可用于发货、退款操作,请入库
			if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
				paymentSuccessCallback(orderSn, tradeNo,type,Cnst.PayMethod.ALIPAY,request);
				result = "success";//请不要修改或删除
			} else {
				//支付成功,平台上订单状态修改失败
				result = "fail";
				log.error("-----------------------------------------------------------------------------------------------");
				log.error("订单:[" + orderSn + "]支付宝支付成功，但调用回调失败!!!!!");
				log.error("-----------------------------------------------------------------------------------------------");
			}
		} catch (AlipayExcetion alipayExcetion) {
			alipayExcetion.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public Integer getOrderNotEvaluatedCount(Map map) {
		return orderMapper.getOrderNotEvaluatedCount(map);
	}

	//众筹订单支付后调用
	public void paymentSuccessCallback(String orderSn, String tradeNo,Integer type,Integer payMehtod,HttpServletRequest request){
		Order order =getByOrderSn(orderSn);
		if(order == null){
			log.error("订单[" + orderSn + "]不存在，但已支付完成");
		}else if (type==Cnst.Order.Type.ZC){//众筹订单
			if(Cnst.Order.Status.WAIT_PAY==order.getOrderStatus()){
				paySuccess(orderSn,tradeNo,payMehtod,request);
			}else{
				log.warn("订单[" + orderSn + "]已经完成了支付，请勿重复支付");
			}
		}else if (type==Cnst.Order.Type.MS){//免税商品订单
			if(Cnst.Order.Status.WAIT_PAY==order.getOrderStatus()){
				paySuccess(orderSn,tradeNo,payMehtod,request);
			}else{
				log.warn("订单[" + orderSn + "]已经完成了支付，请勿重复支付");
			}
		}
	}
}
