package com.up72.hq.task;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.CrowdMapper;
import com.up72.hq.dao.VideoMapper;
import com.up72.hq.dto.resp.CrowdResp;
import com.up72.hq.dto.resp.VideoResp;
import com.up72.hq.model.Crowd;
import com.up72.hq.model.Video;
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
public class CrowdTask {

    @Autowired
    private CrowdMapper crowdMapper;
    /**
     * 每1分钟执行定时任务
     * 根据时间更新评选状态
     */
    @Scheduled(cron="0 0/1 * * * ? ")
    public void executeTimedTask() {
        try {
            //众筹
            Long nowTime= Cnst.getCurTime();
            Map<String,Object> params=new HashMap<String,Object>();
            PageList<CrowdResp>  crowdRespList =crowdMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
            if(ObjectUtils.isNotEmpty(crowdRespList) || crowdRespList.size()>0){
                for (int i = 0; i < crowdRespList.size(); i++) {
                    CrowdResp crowds =crowdRespList.get(i);
                    Crowd crowd = new Crowd();
                    crowd.setId(crowds.getId());
                    crowd.setCatId(crowds.getCatId());
                    crowd.setName(crowds.getName());
                    crowd.setStartTime(crowds.getStartTime());
                    crowd.setEndTime(crowds.getEndTime());
                    crowd.setImg(crowds.getImg());
                    crowd.setDays(crowds.getDays());
                    crowd.setTargetAmount(crowds.getTargetAmount());
                    crowd.setProjectIntro(crowds.getProjectIntro());
                    crowd.setProjectOverview(crowds.getProjectOverview());
                    crowd.setAddTime(crowds.getAddTime());
                    crowd.setStatus(crowds.getStatus());
                    Long startTime=crowds.getStartTime();
                    Long endTime=crowds.getEndTime();
                    if(startTime<=nowTime&&startTime<endTime&&nowTime<endTime){
                        crowd.setStatus(Cnst.HqCrowd.START_CROWD);
                    }else if (nowTime>endTime){
                        if(ObjectUtils.isEmpty(crowds.getRealMoney())){
                            crowd.setStatus(Cnst.HqCrowd.FAIL_CROWD);
                        }else{
                            if(Double.parseDouble(crowds.getRealMoney())>=crowds.getTargetAmount()){
                                crowd.setStatus(Cnst.HqCrowd.SUCCESS_CROWD);
                            }else{
                                crowd.setStatus(Cnst.HqCrowd.FAIL_CROWD);
                            }
                        }
                    }
                    crowdMapper.update(crowd);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0 0 1 * * ?") //每天凌晨1点执行
    public void executeTimedTask1() {
        try {
            //众筹
            Long nowTime= Cnst.getCurTime();
            Map<String,Object> params=new HashMap<String,Object>();
            params.put("status",Cnst.HqNews.YES_STATUS);
            PageList<CrowdResp>  crowdRespList =crowdMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
            if(ObjectUtils.isNotEmpty(crowdRespList) || crowdRespList.size()>0){
                for (int i = 0; i < crowdRespList.size(); i++) {
                    CrowdResp crowds =crowdRespList.get(i);
                    Crowd crowd = new Crowd();
                    crowd.setId(crowds.getId());
                    crowd.setCatId(crowds.getCatId());
                    crowd.setName(crowds.getName());
                    crowd.setStartTime(crowds.getStartTime());
                    crowd.setEndTime(crowds.getEndTime());
                    crowd.setImg(crowds.getImg());
                    crowd.setDays(crowds.getDays()-1);
                    crowd.setTargetAmount(crowds.getTargetAmount());
                    crowd.setProjectIntro(crowds.getProjectIntro());
                    crowd.setProjectOverview(crowds.getProjectOverview());
                    crowd.setAddTime(crowds.getAddTime());
                    if(crowds.getDays()>=0){
                        crowd.setStatus(crowds.getStatus());
                    }else{
                        if(ObjectUtils.isEmpty(crowds.getRealMoney())){
                            crowd.setStatus(Cnst.HqCrowd.FAIL_CROWD);
                        }else{
                            if(Long.parseLong(crowds.getRealMoney())>=crowds.getTargetAmount()){
                                crowd.setStatus(Cnst.HqCrowd.SUCCESS_CROWD);
                            }else{
                                crowd.setStatus(Cnst.HqCrowd.FAIL_CROWD);
                            }
                        }
                    }
                    crowdMapper.update(crowd);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
