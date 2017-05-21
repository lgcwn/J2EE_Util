package com.up72.hq.task;

import com.up72.framework.util.ObjectUtils;
import com.up72.framework.util.page.PageBounds;
import com.up72.framework.util.page.PageList;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.ChooseMapper;
import com.up72.hq.dao.QuizMapper;
import com.up72.hq.dto.resp.ChooseResp;
import com.up72.hq.model.Quiz;
import com.up72.hq.model.QuizQuestio;
import com.up72.hq.service.impl.QuizQuestioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 竞猜的定时任务
 */
@Service
@Transactional
public class QuizTask {

    @Autowired
    private QuizMapper quizMapper;
    @Autowired
    private QuizQuestioServiceImpl quizQuestioServiceImpl;

    /**
     * 每1分钟执行定时任务
     * 根据时间更新评选状态
     */
    @Scheduled(cron="0 0/1 * * * ? ")
    public void executeTimedTask() {
        try {
            //1.更改竞猜状态
            Long nowTime= Cnst.getCurTime();
            Map<String,Object> params=new HashMap<String,Object>();
            PageList<Quiz>  quizList =quizMapper.findPages(params, new PageBounds(1, Integer.MAX_VALUE));
            if(ObjectUtils.isNotEmpty(quizList) || quizList.size()>0){
                for (int i = 0; i < quizList.size(); i++) {
                    Quiz quiz = quizList.get(i);
                    if (quiz.getAutoOrManually()==Cnst.HqNews.AUTO) {
                        Long startTime = quiz.getStartTime();
                        Long endTime = quiz.getEndTime();

                        List<QuizQuestio> quizQuestios = quizQuestioServiceImpl.getByQuizId(quiz.getId());

                        if (startTime < nowTime && nowTime < endTime) {
                            quiz.setStatus(Cnst.HqNews.YES_STATUS);
                            for (QuizQuestio quizQuestio : quizQuestios) {
                                quizQuestio.setStatus(Cnst.GetQuizQuestion.START);
                                quizQuestioServiceImpl.update(quizQuestio);
                            }
                        } else if (nowTime > endTime) {
                            quiz.setStatus(Cnst.HqNews.NOT_STATUS);
                        }
                        quizMapper.update(quiz);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
