package com.up72.hq.task;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.QuizMapper;
import com.up72.hq.dao.VideoMapper;
import com.up72.hq.dto.resp.VideoResp;
import com.up72.hq.model.Quiz;
import com.up72.hq.model.QuizQuestio;
import com.up72.hq.model.Video;
import com.up72.hq.service.impl.QuizQuestioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单的定时任务
 */
@Service
@Transactional
public class VideoTask {

    @Autowired
    private VideoMapper videoMapper;
    /**
     * 每1分钟执行定时任务
     * 根据时间更新评选状态
     */
    @Scheduled(cron="0 0/1 * * * ? ")
    public void executeTimedTask() {
        try {
            Long nowTime= Cnst.getCurTime();
            Map<String,Object> params=new HashMap<String,Object>();
            PageList<VideoResp>  newList =videoMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
            if(ObjectUtils.isNotEmpty(newList) || newList.size()>0){
                for (int i = 0; i < newList.size(); i++) {
                    Video video =newList.get(i);
                    Long publishTime=video.getPublishTime();
                    if(publishTime>nowTime){
                        video.setStatus(Cnst.HqNews.YES_STATUS);
                    }
                    videoMapper.update(video);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
