package com.up72.hq.task;

import com.up72.framework.util.ObjectUtils;
import com.up72.hq.constant.Cnst;
import com.up72.hq.model.Order;
import com.up72.hq.service.IOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 订单的定时任务
 */
@Service
@Transactional
public class ShopOrderTask {

    @Autowired
    private IOrderService orderService;

    /**
     * 查找可取消的订单，并更新相应库存,1分钟执行一次
     */
    @Scheduled(cron="0 */1 * * * ?")
    public void cancelOrder(){
        //当前时间
        //查找到当前时间已经失效并且未修改为已取消的订单
      /*  List<Order> orderList = orderService.getExpiredOrder(Cnst.getCurTime());
        if(ObjectUtils.isNotEmpty(orderList) && orderList.size() > 0){
            //处理库存
            for(Order order : orderList){
                String code = order.getIdCipher();
                orderService.cancelOrder(order.getMemberId(), code);
            }
            //如果存在，修改订单状态，失效状态，失效时间
           // dhsOrderService.updateExpireOrder(now);
*//*
            //修改各个订单对应商品库存
            for (int i = 0; i < dhsOrderList.size(); i++) {
                dhsOrderService.updateStock(dhsOrderList.get(i));
            }*//*
        }*/
    }

    /**
     * 查找服务中心已收货的、订单状态为 待收货、待自提的，未删除的、并且自动收货时间比当前时间小的，将其状态改为交易成功
     */
    @Scheduled(cron="0 2 * * * ?")
    public void confirmReceived(){
        //自动确认收货间隔天数
        Properties properties = null;
        Integer days = 0;
        try {
            //配置文件获取上次更新的时间戳
            properties = PropertiesLoaderUtils.loadAllProperties("hq100.properties");
            String daysStr = properties.getProperty(Cnst.AUTO_RECEIVED_DAYS);
            days = StringUtils.isNotBlank(daysStr) ? Integer.parseInt(daysStr) : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(days == 0){
            days = Cnst.DEFAULT_AUTO_RECEIVED_DAYS;
        }
        //查找服务中心已收货的、订单状态为 待收货、待自提的，未删除的、并且自动收货时间比当前时间小的，将其状态改为交易成功
        orderService.autoConfirmReceived(Cnst.getCurTime());
    }

}
