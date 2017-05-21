/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2016
 */

package com.up72.hq.service.impl;

import java.util.*;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.*;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dto.resp.ChooseResp;
import com.up72.hq.dto.resp.NewsResp;
import com.up72.hq.model.*;
import com.up72.hq.dao.*;
import com.up72.hq.service.*;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.up72.hq.service.INewsService;

import javax.servlet.http.HttpServletRequest;

/**
 * 资讯DAO实现
 * 
 * @author liuguicheng
 * @version 1.0
 * @since 1.0
 */
@Service
@Transactional
public class NewsServiceImpl implements  INewsService{
	
	@Autowired
	private NewsMapper newsMapper;
	
	public void save(News news){
		if(ObjectUtils.isNotEmpty(news.getFirstImg())){
			news.setIsImg(Cnst.HqNews.HAVE_IMG);
		}else {
			news.setIsImg(Cnst.HqNews.NOT_IMG);
		}
		news.setAddTime(Cnst.getCurTime());
		newsMapper.insert(news);
	}

    public void update(News news){
		newsMapper.update(news);
	}

	@Override
	public void update(News news, long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			News db = newsMapper.findById(ids[i]);
			if (ObjectUtils.isNotEmpty(news.getIsRecommend())) {
				db.setIsRecommend(news.getIsRecommend());
			}
			if (ObjectUtils.isNotEmpty(news.getToHome())) {
				db.setToHome(news.getToHome());
			}
			if (ObjectUtils.isNotEmpty(news.getAutoOrManually())) {
				db.setAutoOrManually(news.getAutoOrManually());
			}
			if (ObjectUtils.isNotEmpty(news.getStatus())) {
				db.setStatus(news.getStatus());
			}
			update(db);
		}
	}

	public void delete(java.lang.Long id){
		newsMapper.delete(id);
	}
    
    @Transactional(readOnly=true)
    public News getById(java.lang.Long id){
		return newsMapper.findById(id);
	}
    @Transactional(readOnly=true)
    public NewsResp getByIdResp(java.lang.Long id){
		return newsMapper.findByIdResp(id);
	}

    @Transactional(readOnly=true)
    public Page<NewsResp> getPage(Map params, PageBounds rowBounds){
        PageList list = newsMapper.findPage(params, rowBounds);
		return new Page<NewsResp>(list,list.getPagination());
	}

	@Override
	public Page<NewsResp> getPageTour(Map params, PageBounds rowBounds) {
		PageList list = newsMapper.findPageTour(params, rowBounds);
		return new Page<NewsResp>(list,list.getPagination());
	}

	//最新资讯
	@Transactional(readOnly=true)
	public void setNewsLimit4(HttpServletRequest request) {
		Map<String,Object> params = new HashMap<>();
		params.put("newOrTravel", Cnst.HqNews.NEWSS);
        params.put("isImg",Cnst.HqNews.HAVE_IMG);

        PageBounds pageBounds = new PageBounds(1,4);
		pageBounds.setOrders(com.up72.framework.util.page.Order.formString("PUBLISH_TIME.DESC"));
		Page<NewsResp> newsPage = getPage(params, pageBounds);
		request.setAttribute("newsList",newsPage.getResult());
	}

	/**
	 * 每1分钟执行定时任务
	 * 检查时间，到点自动发布
	 *//*
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
					if(publishTime>nowTime){
						news.setStatus(Cnst.HqNews.YES_STATUS);
					}
					newsMapper.update(news);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}*/

}
