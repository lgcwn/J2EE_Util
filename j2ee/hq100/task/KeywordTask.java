package com.up72.hq.task;

import com.up72.hq.constant.Cnst;
import com.up72.hq.model.Keyword;
import com.up72.hq.service.IKeywordService;
import com.up72.hq.utils.Pinyin4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by syn on 2016/6/13.
 */
@Service
public class KeywordTask {

    @Autowired
    private IKeywordService keywordService;

    /**
     * 每1分钟执行定时任务
     * 根据时间更新评选状态
     */
   /* @Scheduled(cron="0 0/1 * * * ? ")
    public void updateKeyword(){
        Map<String,Long> keywordMap = new HashMap<String,Long>();
        synchronized (Cnst.keywordTimes){
            keywordMap.putAll(Cnst.keywordTimes);
            Cnst.keywordTimes.clear();
        }
        Keyword keyword = null;

        for (Map.Entry<String, Long> entry : keywordMap.entrySet()) {
            keyword = keywordService.getByKeyword(entry.getKey());
            if(keyword == null){
                keyword = new Keyword();
                keyword.setSearchTimes(entry.getValue());
                keyword.setPinyin(Pinyin4jUtil.converterToSpell(entry.getKey()));
                keyword.setAbbre(Pinyin4jUtil.converterToFirstSpell(entry.getKey()));
                keyword.setKeyword(entry.getKey());
                keyword.setType(Integer.valueOf(entry.getValue().toString()));
                keywordService.save(keyword);
            }else{
                keyword.setSearchTimes(keyword.getSearchTimes() + entry.getValue());
                keywordService.update(keyword);
            }
        }
        keywordService.updateDeltaIndex();
    }*/

}
