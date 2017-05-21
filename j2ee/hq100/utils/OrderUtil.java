package com.up72.hq.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.up72.hq.dto.req.ToSettlement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liuguicheng on 16/5/17.
 */
public class OrderUtil {


    public static List<ToSettlement> getToSettlementParam(String code){

        //订单预览-商品列表信息解密
        String json= Base64.getFromBASE64(code);

        JSONArray obj = JSONArray.parseArray(json);

        //预览信息
        List<ToSettlement> list=new ArrayList<ToSettlement>();
        for(int i=0;i<obj.size();i++){

            JSONObject sett= JSONObject.parseObject(obj.get(i).toString());
            ToSettlement toSettlement=new ToSettlement();
            toSettlement.setCount(sett.getInteger("count"));
            toSettlement.setCartId(sett.getLong("cartId"));
            toSettlement.setGoodsId(sett.getLong("goodsId"));
            list.add(toSettlement);
        }

        return list;
    }





}
