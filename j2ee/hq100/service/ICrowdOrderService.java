/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service;

import com.up72.hq.dto.resp.CrowdOrderResp;
import com.up72.hq.dto.resp.GoodsResp;
import com.up72.hq.dto.resp.OrderResp;
import com.up72.hq.dto.resp.OrderStatusCountResp;
import com.up72.hq.model.CrowdOrder;

import com.up72.framework.util.page.PageBounds;
import java.util.List;
import java.util.Map;
import com.up72.framework.util.page.Page;
import com.up72.hq.model.OrderCrowd;

import javax.servlet.http.HttpServletRequest;


/**
 * 订单接口
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
public interface ICrowdOrderService {

    void save(CrowdOrder crowdOrder);

    void saveOrderCrowd(CrowdOrder crowdOrder,OrderCrowd orderCrowd);

    void update(CrowdOrder crowdOrder);

    void delete(Long id);

    CrowdOrderResp getById(Long id);

    CrowdOrder getId(Long id);

    CrowdOrderResp getByIdCrowd(Long id);

    Page<CrowdOrder> getPage(Map params, PageBounds rowBounds);

    Page<CrowdOrderResp> getByCrowdPage(Map params, PageBounds rowBounds);

    /**
     * 扩展Resp
     * @author haiyi
     * @param params
     * @param rowBounds
     * @return
     */
    Page<CrowdOrderResp> getPageResp(Map params, PageBounds rowBounds);


    /**
     * 订单中心-我的订单
     * @param params
     * @return
     */
    Page<CrowdOrderResp> orderCenterMyOrder(Map params,PageBounds pageBounds);


    /**
     * 订单
     */
    List<CrowdOrderResp> getOrderLists(CrowdOrder crowdOrder);

    /**
     * 查询待付款待收货数量
     */
    List<CrowdOrderResp> getOrderStatusSize(CrowdOrder crowdOrder);
    /**
     * 订单详情
     * @param map code 订单加密id
     * @return
     */
    CrowdOrder orderDetail(Map map);


    /**
     * 取消订单
     * @author liuguicheng
     * @param UserId
     * @param orderId
     * @return
     *
     *
     */
    String cancelOrder(Long UserId,String orderId);


    /**
     * 确认收货
     * @author liuguicheng
     * @param UserId
     * @param orderId
     * @return
     */
    String confirmReceipt(Long UserId,String orderId);




    /**
     * 预览订单
     * @author liuguicheng
     * @param code  商品列表数据
     * @param region 地区
     * @param UserId  用户id
     * @return
     */
    Map previewOrder(Long UserId,String code, String region) ;


    /**
     * 下单
     * @author liuguicheng
     * @param crowdOrder
     * @return
     */
    Map<String,String> placeAnOrder(CrowdOrder crowdOrder,String code,String addressId,String invoiceData) ;

    String saveXnOrJfOrder(Long goodsId,Integer totalAmount,Integer count,String addressId,Integer type,String remark,HttpServletRequest request);


    /**
     * 支付成功后异步处理订单
     * @author liuguicheng
     * @param orderSn
     * @return
     */
    public String paySuccess(String orderSn,String tradeNo,Integer channel,Double total_money,HttpServletRequest request);


    /**
     * 获取已过期的订单
     * @return
     * @param now
     */
    public List<CrowdOrder> getExpiredOrder(Long now);

    /**
     * 取消订单之后的更新库存
     * @param Order
     */
    public void updateStock(CrowdOrder crowdOrder);

    /**
     * SQL修改过期订单为取消
     * @param now
     */
    public void updateExpireOrder(Long now);



    /**
     * 查询订单状态数量
     * @author liuguicheng
     * @param UserId
     * @param type
     * @return
     */
    OrderStatusCountResp getOrderStatusCount(Long UserId,Integer type);

    /**
     * 首页展示最新订单
     */
    CrowdOrder newOrder();


    /**
     * 拒收
     * @param code
     * @return
     */
    String rejection(String code,Integer status);
    /**
     * 拒收订单记录
     * @author haiyi
     * @param request
     * @param pageBounds
     * @return
     */
    void getCanCelOrderPage(HttpServletRequest request, PageBounds pageBounds);


    /**
     * 订单自动确认收货
     * @param now
     */
    public void autoConfirmReceived(Long now);
    /**
     * 服务中心 订单编号获取商品
     * @author haiyi
     * @param orderSn
     */
    public CrowdOrderResp getByOrderSn(String orderSn);

    Long getOrderCnt(Map params);
    /**
     * 获取用户公益订单
     */
    List<CrowdOrderResp> getByCrowd(Long memberId);

    GoodsResp getXnOrJfGoodsResp(Long goodsId, Integer count);

    String wxpayInstantCallback(HttpServletRequest request,Integer type);

    String alipayInstantCallback(HttpServletRequest request,Integer type);
    String alipayInstantCallbackMove(HttpServletRequest request,Integer type);

}
