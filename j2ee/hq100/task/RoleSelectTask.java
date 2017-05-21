package com.up72.hq.task;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.RoleSelectMapper;
import com.up72.hq.dto.resp.RoleSelectResp;
import com.up72.hq.model.RoleSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单的定时任务
 */
@Service
@Transactional
public class RoleSelectTask {

    @Autowired
    private RoleSelectMapper roleSelectMapper;

    /**
     * 每1分钟执行定时任务
     * 根据投票时间范围更新角色选拔状态
     */
    @Scheduled(cron="0 0/1 * * * ? ")
    public void executeTimedTask() {
        try {
            Long nowTime= Cnst.getCurTime();
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("statusNot",Cnst.Choose.RoleSelectStatus.HAD_DOWN_MARKETABLE);
            PageList<RoleSelectResp>  chooseList =roleSelectMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
            if(ObjectUtils.isNotEmpty(chooseList) || chooseList.size()>0){
                for (int i = 0; i < chooseList.size(); i++) {
                    RoleSelect roleSelect=chooseList.get(i);
                    Long startVoteTime=roleSelect.getVoteStartTime();
                    Long endVoteTime=roleSelect.getVoteEndTime();
                    Long startSignTime=roleSelect.getSignStartTime();
                    Long endSignTime=roleSelect.getSignEndTime();
                    if(nowTime>=startSignTime && nowTime<endSignTime){
                        roleSelect.setStatus(Cnst.Choose.RoleSelectStatus.SIGN_STARTING);
                    }else {
                        roleSelect.setStatus(Cnst.Choose.RoleSelectStatus.NO_START);
                    }
                    if(nowTime>=startVoteTime && nowTime<endVoteTime){
                        roleSelect.setStatus(Cnst.Choose.RoleSelectStatus.VOTE_STARTING);
                    }else if (nowTime>=endVoteTime){
                        roleSelect.setStatus(Cnst.Choose.RoleSelectStatus.END);
                    }
                    roleSelectMapper.update(roleSelect);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
