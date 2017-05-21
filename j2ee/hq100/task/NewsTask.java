package com.up72.hq.task;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.ChooseMapper;
import com.up72.hq.dao.NewsMapper;
import com.up72.hq.dto.resp.ChooseResp;
import com.up72.hq.dto.resp.NewsResp;
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
public class NewsTask {

    @Autowired
    private NewsMapper newsMapper;

    /**
     * 每1分钟执行定时任务
     * 根据时间更新评选状态
     */
    @Scheduled(cron="0 0/1 * * * ? ")
    public void executeTimedTask() {
        try {
            Long nowTime= Cnst.getCurTime();
            Map<String,Object> params=new HashMap<String,Object>();
            PageList<NewsResp>  newList =newsMapper.findPage(params, new PageBounds(1, Integer.MAX_VALUE));
            if(ObjectUtils.isNotEmpty(newList) || newList.size()>0){
                for (int i = 0; i < newList.size(); i++) {
                    NewsResp news =newList.get(i);
                    Long publishTime=news.getPublishTime();
                    if(news.getAutoOrManually()==Cnst.HqNews.AUTO){
                    if(publishTime<nowTime){
                        news.setStatus(Cnst.HqNews.YES_STATUS);
                    }
                    newsMapper.update(news);
                }}
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
