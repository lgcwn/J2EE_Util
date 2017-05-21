/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.up72.component.alipay.AlipayConstant;
import com.up72.component.alipay.AlipayDirectHelper;
import com.up72.component.alipay.AlipayHelperSingleton;
import com.up72.component.alipay.exception.AlipayExcetion;
import com.up72.component.pay.common.PayInfoUtil;
import com.up72.component.wxpay.protocol.UnifiedOrderDto;
import com.up72.component.wxpay.service.UnifiedOrderService;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.MemberResp;
import com.up72.hq.dto.resp.RuleManagerOrderResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import com.up72.framework.util.page.PageBounds;

import com.up72.hq.utils.HqUtil;
import com.up72.hq.utils.SNCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.IRuleManagerOrderService;
import com.up72.framework.util.page.PageList;
import com.up72.framework.util.page.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 角色报名订单DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class RuleManagerOrderServiceImpl implements  IRuleManagerOrderService{

	Logger logger = LoggerFactory.getLogger(RuleManagerOrderServiceImpl.class);
	
	@Autowired
	private RuleManagerOrderMapper ruleManagerOrderMapper;

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private RoleManagerMapper roleManagerMapper;

	@Autowired
	private PayListMapper payListMapper;

	@Autowired
	private ScoreListMapper 	scoreListMapper;
	
	public void save(RuleManagerOrder ruleManagerOrder){
		ruleManagerOrderMapper.insert(ruleManagerOrder);
	}

    public void update(RuleManagerOrder ruleManagerOrder){
		ruleManagerOrderMapper.update(ruleManagerOrder);
	}
	
    public void delete(java.lang.Long id){
		ruleManagerOrderMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public RuleManagerOrderResp getById(java.lang.Long id){
		return ruleManagerOrderMapper.findById(id);
	}

    @Transactional(readOnly=true)
    public Page<RuleManagerOrderResp> getPage(Map params, PageBounds rowBounds){
        PageList list = ruleManagerOrderMapper.findPage(params, rowBounds);
		return new Page<RuleManagerOrderResp>(list,list.getPagination());
	}

	@Override
	public Long countNumberByStatus(Integer status,Long roleSelectId) {
		Map<String,Object> params=new HashMap<>();
		params.put("orderStatus",status);
		params.put("roleSelectId",roleSelectId);
		return ruleManagerOrderMapper.countNumber(params);
	}

	@Transactional
	public RuleManagerOrder getBySn(String sn) {
		return ruleManagerOrderMapper.findBySn(sn);
	}

	/**
	 * 微信支付回调
	 * @param request
	 * @return
	 */
	public String wxpayInstantCallback(HttpServletRequest request) {
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
					paymentSuccessCallback(tradeNo,unifiedOrderDto.getTransaction_id(),Cnst.PayMethod.WEIXIN,request);
				}else if(unifiedOrderDto.getReturn_msg() != null && !"".equals(unifiedOrderDto.getReturn_msg())){
					//数据异常
					logger.warn("----error:" + unifiedOrderDto.getReturn_msg());
				}else{
					//失败或者异常
					logger.warn("----fail 订单:" + unifiedOrderDto.getErr_code_des());
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
	 * 支付宝即时交易回调
	 * @param request
	 * @return
	 */
	public String alipayInstantCallback(HttpServletRequest request) {
		String result = "success";
		try {
			Map requestParams = request.getParameterMap();
			AlipayDirectHelper helper = null;
			helper = (AlipayDirectHelper) AlipayHelperSingleton.getInstance().findUnSynchronizedResult(requestParams, AlipayConstant.SERVICE_DIRECT,false);
			String trade_status = helper.getTrade_status();//支付状态
			String orderSn = helper.getOut_trade_no();//唯一订单号，查询使用
			String tradeNo = helper.getTrade_no();//支付产生在交易号，该交易号可用于发货、退款操作,请入库
			if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
				paymentSuccessCallback(orderSn, tradeNo,Cnst.PayMethod.ALIPAY,request);
				result = "success";//请不要修改或删除
			} else {
				//支付成功,平台上订单状态修改失败
				result = "fail";
				logger.error("-----------------------------------------------------------------------------------------------");
				logger.error("订单:[" + orderSn + "]支付宝支付成功，但调用回调失败!!!!!");
				logger.error("-----------------------------------------------------------------------------------------------");
			}
		} catch (AlipayExcetion alipayExcetion) {
			alipayExcetion.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 支付宝移动端即时交易回调
	 * @param request
	 * @return
	 */
	public String wapAlipayInstantCallback(HttpServletRequest request) {
		String result = "success";
		try {
			Map requestParams = request.getParameterMap();
			AlipayDirectHelper helper = null;
			helper = (AlipayDirectHelper) AlipayHelperSingleton.getInstance().findUnSynchronizedResult(requestParams, AlipayConstant.SERVICE_DIRECT,false);
			String trade_status = helper.getTrade_status();//支付状态
			String orderSn = helper.getOut_trade_no();//唯一订单号，查询使用
			String tradeNo = helper.getTrade_no();//支付产生在交易号，该交易号可用于发货、退款操作,请入库
			if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
				paymentSuccessCallback(orderSn, tradeNo,Cnst.PayMethod.ALIPAY,request);
				result = "success";//请不要修改或删除
			} else {
				//支付成功,平台上订单状态修改失败
				result = "fail";
				logger.error("-----------------------------------------------------------------------------------------------");
				logger.error("订单:[" + orderSn + "]支付宝支付成功，但调用回调失败!!!!!");
				logger.error("-----------------------------------------------------------------------------------------------");
			}
		} catch (AlipayExcetion alipayExcetion) {
			alipayExcetion.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public boolean loginUserIsSignUp(Map params) {
		int number=ruleManagerOrderMapper.countSignUp(params);
		if(number==0){
			return false;
		}
		return  true;
	}


	public void paymentSuccessCallback(String orderSn, String tradeNo,Integer payMehtod,HttpServletRequest request){
		RuleManagerOrder order =getBySn(orderSn);
		if(order == null){
			logger.error("角色选拔订单[" + orderSn + "]不存在，但已支付完成");
		}else{
			if(Cnst.Choose.RuleManagerOrder.Status.WAIT_PAY==order.getOrderStatus()){

			RoleManager roleManager = roleManagerMapper.findId(order.getRoleManagerId());


				order.setTradeNo(tradeNo);
				order.setOrderStatus(Cnst.Choose.RuleManagerOrder.Status.WAIT_AUDIT);
				order.setPaymentDate(Cnst.getCurTime());
				order.setPayChannel(payMehtod);
				order.setPayTime(Cnst.getCurTime());
				ruleManagerOrderMapper.update(order);
				double money=order.getActualPayments();
				int score=roleManager.getGivePoints();
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
				scoreList.setRemark("角色选拔报名【订单(" + order.getSn() + ")】，获得" + score+"积分");
				scoreList.setTbOrJf(2);
				scoreListMapper.insert(scoreList);

				if(order.getIsUseScore()==Cnst.Order.IsUseScore_YES){
					member.setScore(member.getScore()-order.getScoreCount());
				}
				memberMapper.update(member);
				HqUtil.setSessionByMember(request,member);

				PayList payList=new PayList();
				payList.setAddDate(Cnst.getCurTime());

				payList.setBillType(0);

				payList.setMemberId(order.getMemberId());

				Integer type=0;

				if(payMehtod == Cnst.PayMethod.ALIPAY) //支付宝
					type=2;
				if(payMehtod == Cnst.PayMethod.UNIONPAY) //银联
					type=4;
				if(payMehtod == Cnst.PayMethod.WEIXIN) //微信
					type=3;
				if(payMehtod == 4) //余额
					type=1;

				payList.setPayType(type);

				payList.setCardType(0);

				payList.setMoney(order.getActualPayments());

				payList.setReMark("角色选拔订单编号:"+order.getSn()+",支付金额:"+order.getActualPayments());

				payList.setStatus(0);

				payList.setSn(SNCode.getTradeNo());

				payListMapper.insert(payList);
			}else{
				logger.warn("角色选拔订单[" + orderSn + "]已经完成了支付，请勿重复支付");
			}
		}
	}
	

}
