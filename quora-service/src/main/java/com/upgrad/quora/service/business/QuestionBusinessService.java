package com.upgrad.quora.service.business;


import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBusinessService {

    @Autowired
    private QuestionDao questionDao;


    public List<QuestionEntity> getAllQuestions(){

        List<QuestionEntity> questionEntities = questionDao.getAllQuestions();
        return questionEntities;
    }

}