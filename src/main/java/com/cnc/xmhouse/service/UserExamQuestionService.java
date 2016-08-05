package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.Exam;
import com.cnc.xmhouse.lagacy_entity.Question;
import com.cnc.xmhouse.lagacy_entity.UserExam;
import com.cnc.xmhouse.lagacy_entity.UserExamQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hongzhan on 2016/7/28.
 */
@Service
public class UserExamQuestionService {

    @Autowired
    private BaseDao<UserExamQuestion> ueqDao;
    @Autowired
    private BaseDao<Question> questionDao;
    @Autowired
    private BaseDao<UserExam>ueDao;
    @Autowired
    private BaseDao<Exam>eDao;

    static final private String SPLITTER = ";";


    public void clearOldUserExamResult(long userExamId){
        ueqDao.executeUpdateHql("delete from UserExamQuestion where userExamId=?", userExamId);
    }

    public int saveUserExamResult(List<UserExamQuestion> list,long ueid) {
        if (list == null) {
            return -1;
        }
        boolean saveSuccess = true;
        int wrongCount = 0;

        for (UserExamQuestion ueq : list) {
            boolean isRight = isRight(ueq);
            if(!isRight)
                wrongCount++;
            ueq.setIsRight(isRight ? (byte) 1 : (byte) 0);
            ueq.setUserExamId(ueid);
            saveSuccess = ueqDao.save(ueq) == null ? false : true;
        }
        return saveSuccess ? (int) Math.round((100.0 / list.size()) * (list.size() - wrongCount)) : -1;
    }

    public int reJudge(long ueqId,boolean result){
        UserExamQuestion q=(UserExamQuestion)ueqDao.findUnique("id", ueqId, UserExamQuestion.class);
        q.setIsRight((byte) (result?1:0));
        ueqDao.update(q);
        List<UserExamQuestion> list = ueqDao.query(" from UserExamQuestion where userExamId=?", q.getUserExamId());
        int wrongCount=0;
        for(UserExamQuestion ueq:list){
            if(ueq.getIsRight()==0)
                wrongCount++;
        }
        int score=(int) Math.round((100.0 / list.size()) * (list.size() - wrongCount));
        UserExam ue= (UserExam) ueDao.findUnique("id",q.getUserExamId(),UserExam.class);
        Exam exam= (Exam) eDao.findUnique("id", ue.getExamId(), Exam.class);
        ue.setScore(score);
        if(score>=exam.getPassMark()){
            ue.setStatus(1);
            ue.setFailCount(ue.getFailCount()>0? ue.getFailCount()-1:0);
        }
        ueDao.update(ue);
        return score;
    }

    private boolean isRight(UserExamQuestion ueq) {
        String standardAnsString = ((Question) questionDao.findUnique("id", ueq.getQuestionId(), Question.class)).getAns();
        boolean isRight = true;
        try {
            String[] userAns = ueq.getAns().split(SPLITTER);
            String[] standardAns = standardAnsString.split(SPLITTER);
            int blockCount = userAns.length > standardAns.length ? userAns.length : standardAns.length;
            for (int i = 0; i < blockCount; i++) {
                if (!userAns[i].equalsIgnoreCase(standardAns[i])) {
                    throw new WrongAnswerException();
                }
            }
        } catch (Exception e) {
            isRight = false;
        }
        return isRight;
    }

    static public class WrongAnswerException extends Exception {

    }
}
