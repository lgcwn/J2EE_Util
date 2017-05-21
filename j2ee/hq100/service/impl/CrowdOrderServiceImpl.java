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
import com.up72.hq.service.ICrowdOrderService;
import com.up72.hq.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
public class CrowdOrderServiceImpl implements  ICrowdOrderService{

	private static final Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private MemberMessageMapper memberMessageMapper;
	@Autowired
	private CrowdOrderMapper crowdOrderMapper;
	@Autowired
	private OrderCrowdMapper orderCrowdMapper;
	@Autowired
	private ExchangePointsMapper exchangePointsMapper;
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

	public void save(CrowdOrder order){
		crowdOrderMapper.insert(order);
	}

    @Override
    public void saveOrderCrowd(CrowdOrder order, OrderCrowd orderCrowd) {
		save(order);
		orderCrowd.setOrderId(order.getId());
		orderCrowd.setGoodsPrice(order.getActualPayments());
		orderCrowd.setGoodsCount(1);
		orderCrowd.setCreateDate(Cnst.getCurTime());
		orderCrowd.setGoodsSn(order.getSn());
		orderCrowdServiceImpl.save(orderCrowd);
    }

    @Override
    public void update(CrowdOrder order) {
		crowdOrderMapper.update(order);
    }

    @Override
    public void delete(Long id) {
		CrowdOrder order=crowdOrderMapper.findById(id);

		order.setIsDelete(1);

		crowdOrderMapper.update(order);
    }

    @Override
    public CrowdOrderResp getById(Long id) {
        return crowdOrderMapper.findById(id);
    }

    @Override
    public CrowdOrder getId(Long id) {
        return crowdOrderMapper.findId(id);
    }

    @Override
    public CrowdOrderResp getByIdCrowd(Long id) {
        return crowdOrderMapper.findByIdCrowd(id);
    }

    @Override
    public Page<CrowdOrder> getPage(Map params, PageBounds rowBounds) {

		PageList list = crowdOrderMapper.findPage(params, rowBounds);
		return new Page<CrowdOrder>(list,list.getPagination());
    }

    @Override
    public Page<CrowdOrderResp> getByCrowdPage(Map params, PageBounds rowBounds) {

		PageList list = crowdOrderMapper.findByCrowdPage(params, rowBounds);
		return new Page<CrowdOrderResp>(list,list.getPagination());
    }

    @Override
    public Page<CrowdOrderResp> getPageResp(Map params, PageBounds rowBounds) {

		PageList list = crowdOrderMapper.findPageResp(params, rowBounds);
		Page<CrowdOrderResp> page = new Page<CrowdOrderResp>(list,list.getPagination());
		return page;
    }

    @Override
    public Page<CrowdOrderResp> orderCenterMyOrder(Map params, PageBounds pageBounds) {
        return null;
    }

    @Override
    public List<CrowdOrderResp> getOrderLists(CrowdOrder Order) {
        return null;
    }

    @Override
    public List<CrowdOrderResp> getOrderStatusSize(CrowdOrder Order) {
        return null;
    }

    @Override
    public CrowdOrder orderDetail(Map map) {
        return null;
    }

    @Override
    public String cancelOrder(Long UserId, String orderId) {
        return null;
    }

    @Override
    public String confirmReceipt(Long UserId, String orderId) {
        return null;
    }

    @Override
    public Map previewOrder(Long memberId, String code, String region) {
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

    @Override
    public Map<String, String> placeAnOrder(CrowdOrder Order, String code, String addressId, String invoiceData) {
        return null;
    }

    @Override
    public String saveXnOrJfOrder(Long goodsId, Integer totalAmount, Integer count, String addressId, Integer type, String remark, HttpServletRequest request) {
        return null;
    }

    @Override
    public String paySuccess(String orderSn, String tradeNo, Integer channel, Double total_money,HttpServletRequest request) {
		log.warn("【众筹订单支付】pay success: orderSn:"+orderSn+" tradeNo:"+tradeNo+" channel: "+channel);
		CrowdOrderResp order=crowdOrderMapper.findBySn(orderSn);
		if(order != null ){

			CrowdOrder crowdOrder = crowdOrderMapper.findId(order.getId());
			ExchangePoints crowdPoints = exchangePointsMapper.findById(2L);
			OrderCrowdResp orderCrowd = orderCrowdMapper.findByOrderId(order.getId());
			crowdOrder.setOrderStatus(Cnst.Order.Status.WAIT_SEND);
			crowdOrder.setPaymentDate(Cnst.getCurTime());
			crowdOrder.setPayTime(Cnst.getCurTime());
			crowdOrder.setPayChannel(channel);
			crowdOrder.setTradeNo(tradeNo);
			crowdOrder.setActualPayments(total_money);
			double money=order.getActualPayments();
			int score=0;
			if(orderCrowd.getType()==0){
				crowdOrder.setOrderStatus(Cnst.Order.Status.SUCCESS);
				score = (int)(money/crowdPoints.getRatio());
			}else{
				score = orderCrowd.getReturnInfo().getGivePoints();
			}
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
			scoreList.setRemark("众筹【订单(" + order.getSn() + ")】，获得" + score +"积分");
			scoreList.setTbOrJf(2);
			scoreListMapper.insert(scoreList);
			memberMapper.update(member);
			HqUtil.setSessionByMember(request,member);

			crowdOrderMapper.update(crowdOrder);
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

			//用户消息
			MemberMessage memberMessage=new MemberMessage();
			memberMessage.setTitle("购买众筹收入积分" + score);
			memberMessage.setContent("亲爱的会员：您于" + order.getPayTimeStr() + "到账" + score + "积分");
			memberMessage.setAddTime(Cnst.getCurTime());
			memberMessage.setStatus(0);
			memberMessage.setHqMemberId(member.getId());
			memberMessage.setIsDel(0);
			memberMessage.setType(2);
			memberMessageMapper.insert(memberMessage);

			//用户消息
			memberMessage=new MemberMessage();
			memberMessage.setTitle("众筹订单支付成功");
			memberMessage.setContent("亲爱的会员：您的订单【"+orderSn+"】于" + order.getPayTimeStr() + "支付成功，谢谢您的支持！");
			memberMessage.setAddTime(Cnst.getCurTime());
			memberMessage.setStatus(0);
			memberMessage.setHqMemberId(order.getMemberId());
			memberMessage.setIsDel(0);
			memberMessage.setType(2);
			memberMessageMapper.insert(memberMessage);

		}

		return "success";
    }

    @Override
    public List<CrowdOrder> getExpiredOrder(Long now) {
        return null;
    }

    @Override
    public void updateStock(CrowdOrder Order) {

    }

    @Override
    public void updateExpireOrder(Long now) {

    }

    @Override
    public OrderStatusCountResp getOrderStatusCount(Long memberId, Integer type) {

		OrderStatusCountResp orderStatusCountResp=new OrderStatusCountResp();
		Map map=new HashMap<>();
		map.put("isDelete", "0");
		map.put("type", type);
		if (ObjectUtils.isNotEmpty(type)){
			map.put("memberId",memberId);
		}
		List<CrowdOrderResp> orderResps=crowdOrderMapper.getOrderStatusCount(map);
		for(CrowdOrderResp orderResp:orderResps){
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
		Integer count=crowdOrderMapper.getNewOrderCount(map);
		orderStatusCountResp.setNewOrderCount(count);
		//待评价
		map.put("orderStatus",Cnst.Order.Status.SUCCESS);
		map.put("isComment",0);
		orderStatusCountResp.setToBeEvaluated(crowdOrderMapper.getOrderNotEvaluatedCount(map));
		return orderStatusCountResp;
    }

    @Override
    public CrowdOrder newOrder() {
        return null;
    }

    @Override
    public String rejection(String code, Integer status) {
        return null;
    }

    @Override
    public void getCanCelOrderPage(HttpServletRequest request, PageBounds pageBounds) {

    }

    @Override
    public void autoConfirmReceived(Long now) {

    }

    @Override
    public CrowdOrderResp getByOrderSn(String orderSn) {
        return crowdOrderMapper.findBySn(orderSn);
    }

    @Override
    public Long getOrderCnt(Map params) {
        return crowdOrderMapper.findOrderCnt(params);
    }

    @Override
    public List<CrowdOrderResp> getByCrowd(Long memberId) {
        return crowdOrderMapper.findByCrowd(memberId);
    }

    @Override
    public GoodsResp getXnOrJfGoodsResp(Long goodsId, Integer count) {
        return null;
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
					double total_money=Double.parseDouble(unifiedOrderDto.getTotal_fee())/100;
					paymentSuccessCallback(tradeNo,unifiedOrderDto.getTransaction_id(),type,Cnst.PayMethod.WEIXIN,total_money,request);
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
				paymentSuccessCallback(orderSn, tradeNo,type,Cnst.PayMethod.ALIPAY, Double.valueOf(helper.getTotal_fee()),request);
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
	/*public String alipayInstantCallback_Test(HttpServletRequest request,Integer type,String orderSn,String tradeNo,Double money) {
		String result = "success";
		try {
			Map requestParams = request.getParameterMap();
			String trade_status = "TRADE_FINISHED";//支付状态
//			String orderSn = helper.getOut_trade_no();//唯一订单号，查询使用
//			String tradeNo = helper.getTrade_no();//支付产生在交易号，该交易号可用于发货、退款操作,请入库
			if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
				paymentSuccessCallback(orderSn, tradeNo,type,Cnst.PayMethod.ALIPAY, money,request);
				result = "success";//请不要修改或删除
			} else {
				//支付成功,平台上订单状态修改失败
				result = "fail";
				log.error("-----------------------------------------------------------------------------------------------");
				log.error("订单:[" + orderSn + "]支付宝支付成功，但调用回调失败!!!!!");
				log.error("-----------------------------------------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/

	/**
	 * 众筹支付宝即时交易回调-移动端
	 * @param request
	 * @return
	 */
	public String alipayInstantCallbackMove(HttpServletRequest request,Integer type) {
		String result = "success";
		try {
			Map requestParams = request.getParameterMap();
			AlipayDirectHelper helper = null;
			helper = (AlipayDirectHelper) AlipayHelperSingleton.getInstance().findUnSynchronizedResult(requestParams, AlipayConstant.SERVICE_DIRECT,true);
			String trade_status = helper.getTrade_status();//支付状态
			String orderSn = helper.getOut_trade_no();//唯一订单号，查询使用
			String tradeNo = helper.getTrade_no();//支付产生在交易号，该交易号可用于发货、退款操作,请入库
			if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
				paymentSuccessCallback(orderSn, tradeNo,type,Cnst.PayMethod.ALIPAY, Double.valueOf(helper.getTotal_fee()),request);
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

	//众筹订单支付后调用
	public void paymentSuccessCallback(String orderSn, String tradeNo,Integer type,Integer payMehtod,Double total_money,HttpServletRequest request){
		CrowdOrder order =getByOrderSn(orderSn);
		if(order == null){
			log.error("订单[" + orderSn + "]不存在，但已支付完成");
		}else if (type==Cnst.Order.Type.ZC){//众筹订单
			if(Cnst.Order.Status.WAIT_PAY==order.getOrderStatus()){
				paySuccess(orderSn,tradeNo,payMehtod,total_money,request);
			}else{
				log.warn("订单[" + orderSn + "]已经完成了支付，请勿重复支付");
			}
		}
	}
}
