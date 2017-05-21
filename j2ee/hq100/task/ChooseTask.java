package com.up72.hq.task;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.ChooseMapper;
import com.up72.hq.dto.resp.ChooseResp;
import com.up72.hq.model.Order;
import com.up72.hq.service.IOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 订单的定时任务
 */
@Service
@Transactional
public class ChooseTask {

    @Autowired
    private ChooseMapper chooseMapper;

    /**
     * 每1分钟执行定时任务
     * 根据时间更新评选状态
     */
    @Scheduled(cron="0 0/1 * * * ? ")
    public void executeTimedTask() {
        try {
            //1.查看订单是否失效
            Long nowTime= Cnst.getCurTime();
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("statusNot",Cnst.Choose.Status.HAD_DOWN_MARKETABLE);
            PageList<ChooseResp> chooseList =chooseMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
            if(ObjectUtils.isNotEmpty(chooseList) || chooseList.size()>0){
                for (int i = 0; i < chooseList.size(); i++) {
                    ChooseResp choose=chooseList.get(i);
                    Long startTime=choose.getStartTime();
                    Long endTime=choose.getEndTime();
                    if(startTime<nowTime){
                        choose.setStatus(Cnst.Choose.Status.NO_START);
                    }
                    if(nowTime>=startTime && nowTime<endTime){
                        choose.setStatus(Cnst.Choose.Status.STARTING);
                    }else if (nowTime>=endTime ){
                        choose.setStatus(Cnst.Choose.Status.END);
                    }
                    chooseMapper.update(choose);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
