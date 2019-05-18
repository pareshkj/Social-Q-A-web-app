package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * DAO class used for answer related database operations.
 */
@Repository
public class AnswerDao {

    @PersistenceContext
    EntityManager entityManager;

    /**
     * method used for creating and answer row in database.
     *
     * @param answer answer object to be created
     * @return created answer object
     */
    public Answer createAnswer(Answer answer) {
        entityManager.persist(answer);
        return answer;
    }

    /**
     * method used for getting answer for a uuid.
     * returns null if object does not exist
     *
     * @param answerUuId answeruuid string object
     * @return Answer object for the specific uuid
     */
    public Answer getAnswerForUuId(String answerUuId) {
        try {
            return entityManager
                    .createNamedQuery("getAnswerForUuId", Answer.class)
                    .setParameter("uuid", answerUuId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * method used for editing and answer.
     *
     * @param answer answer object to be edited
     * @return edited answer object
     */
    public Answer editAnswer(Answer answer) {
        entityManager.persist(answer);
        return answer;
    }

    /**
     * method used for deleting an answer.
     *
     * @param answer answer object to be deleted
     */
    public void deleteAnswer(Answer answer) {
        entityManager.remove(answer);
    }

    /**
     * method used for getting answer for a specific question from database.
     * returns null if no answers are there in the database for the specific question.
     *
     * @param questionUuId question uuid for whom the answer list is required
     * @return List of answers
     */
    public List<Answer> getAnswersForQuestion(String questionUuId) {
        try {
            return entityManager.createNamedQuery("getAnsersForQuestion", Answer.class)
                    .setParameter("uuid", questionUuId)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
