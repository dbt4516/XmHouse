package com.cnc.xmhouse.service;

import com.cnc.xmhouse.dao.BaseDao;
import com.cnc.xmhouse.lagacy_entity.ExamStatistices;
import com.cnc.xmhouse.lagacy_entity.UserExamDetail;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by hongzhan on 2016/7/29.
 */
@Service
public class ExamStatisService {

    @Autowired
    private BaseDao<Object>dao;

    public List<UserExamDetail> query(Long st, Long et, String name, Integer course, Integer courseType, Integer status){
        List<String>conditions=new LinkedList<>();
        if(st!=null){
            conditions.add(String.format("ust >= FROM_UNIXTIME( %d)",st/1000));
        }
        if(et!=null){
            conditions.add(String.format("uet <= FROM_UNIXTIME( %d)",et/1000));
        }
        if(name!=null){
            conditions.add("u_name like '%"+name+"%'");
        }
        if(course!=null){
            conditions.add(String.format("cid=%d",course));
        }
        if(courseType!=null){
            conditions.add(String.format("c_type=%d",courseType));
        }
        if(status!=null){
            conditions.add(String.format("status=%d",status));
        }
        String s= Joiner.on(" and ").join(conditions);
        String where =s.equals("")? "":"where ";
        return UserExamDetail.parseForQuery(dao.querySql("select * from user_exam_detail  "+where+s+" order by uid,d_name,cid asc"));
    }

    public List<UserExamDetail>queryByExamId(long examId){
        return UserExamDetail.parseForQuery(dao.querySql("select * from user_exam_detail  where eid="+examId));
    }

    public List<UserExamDetail> leaderQuery(Long st, Long et, Integer course, int leaderId){
        List<String>conditions=new LinkedList<>();
        if(st!=null){
            conditions.add(String.format("e_create_time >= FROM_UNIXTIME( %d)",st/1000));
        }
        if(et!=null){
            conditions.add(String.format("e_create_time <= FROM_UNIXTIME( %d)",et/1000));
        }
        if(course!=null){
            conditions.add(String.format("cid=%d",course));
        }
        conditions.add("leader_id="+leaderId);
        String s= Joiner.on(" and ").join(conditions);
        String where =" where ";
        return UserExamDetail.parseForQuery(dao.querySql("select * from user_exam_detail  "+where+s+" order by cid,eid,uid asc"));
    }
    /**
     * 统计查询的考试列表
     * @param st
     * @param et
     * @param courseId
     * @param categoryType
     * @return
     */
    public List<UserExamDetail> queryExams(Long st, Long et, Integer courseId, Integer categoryType){
        List<String>conditions=new LinkedList<>();
        if(st!=null){
            conditions.add(String.format("ust >= FROM_UNIXTIME( %d)",st/1000));
        }if(et!=null){
            conditions.add(String.format("uet <= FROM_UNIXTIME( %d)",et/1000));
        }if(courseId!=null){
            conditions.add(String.format("cid=%d",courseId));
        }if(categoryType!=null){
            conditions.add(String.format("c_type=%d",categoryType));
        }
        String s= Joiner.on(" and ").join(conditions);
        String where =s.equals("")? "":"where ";
        return UserExamDetail.parseForQuery(dao.querySql("select * from user_exam_detail  "+where+s+" order by eid,d_name asc"));
    }
    /**
     * 考试统计查询
     * 管理员根据时间，课程类型，课程查询课程的考试情况
     * 考试情况包括考试总人数，未通过人数，通过人数，通过率，请假人数，考试时间等信息
     * @param st
     * @param et
     * @param courseId
     * @param categoryType
     * @return
     */
    public List<ExamStatistices> getExamStatistices(Long st, Long et, Integer courseId, Integer categoryType){

        List<UserExamDetail> exams = queryExams(st,et,courseId,categoryType);
        if(null == exams || exams.size() == 0)
            return new ArrayList<ExamStatistices>();
        List<ExamStatistices> examStatisticesList = new ArrayList<>();
        Long examId = exams.get(0).getEid();
        UserExamDetail userExamDetail = null;
        Long eid;
        Integer status;//1:passed,2:exempted,3:leave;-1 fail
        Integer count = 0,passCount = 0,leaveCount = 0,failCount = 0;
        DecimalFormat df= new DecimalFormat("######0.00");
        for(int i = 0;i<exams.size();i++){
            userExamDetail = exams.get(i);
            eid = userExamDetail.getEid();
            status = userExamDetail.getStatus();
            if( eid == examId){
                count++;
                if(status == 1) passCount++;
                else if(status == 3) leaveCount++;
                else if(status == -1) failCount ++;
            }else{
                examStatisticesList.add(new ExamStatistices(examId,exams.get(i-1).geteName(),
                        count,failCount,passCount,100 * Double.parseDouble(df.format((double)passCount/(double)count)),
                        leaveCount, exams.get(i-1).getEst(), exams.get(i-1).getEet()));
                examId = eid;
                count = 1;
                if(status == 1) {passCount = 1; leaveCount = 0;failCount = 0;}
                else if(status == 3) {passCount = 0; leaveCount = 1;failCount = 0;}
                else if(status == -1) {passCount = 0; leaveCount = 0;failCount = 1;}
                else {passCount = 0; leaveCount = 0;failCount = 0;}
            }
        }
        examStatisticesList.add(new ExamStatistices(userExamDetail.getEid(),userExamDetail.geteName(),
                count,failCount,passCount,100 * Double.parseDouble(df.format((double)passCount/(double)count)),
                leaveCount, userExamDetail.getEst(), userExamDetail.getEet()));
        return examStatisticesList;
    }

    /**
     * 统计各部门的考试情况：（管理员和超级管理员）
     *管理员可以根据时间和课程获得考试的部门统计信息。
     *统计信息包括考试总人数，未通过人数，通过人数，通过率。
     *按部门统计。
     * @param st
     * @param et
     * @param courseId
     * @return
     */
    public List<ExamStatistices> getDeptStatistices(Long st, Long et, Integer courseId){
        List<UserExamDetail> exams = queryExams(st,et,courseId,null);
        if(null == exams || exams.size() == 0)
            return new ArrayList<ExamStatistices>();
        List<ExamStatistices> examStatisticesList = new ArrayList<>();
        Long examId = exams.get(0).getEid();
        String examDept = exams.get(0).getdName();
        UserExamDetail userExamDetail = null;
        Long eid;
        String dName;
        Integer status;//1:passed,2:exempted,3:leave;-1 fail
        Integer count = 0,passCount = 0,failCount = 0;
        DecimalFormat df= new DecimalFormat("######0.00");
        for(int i = 0;i<exams.size();i++){
            userExamDetail = exams.get(i);
            eid = userExamDetail.getEid();
            dName = userExamDetail.getdName();
            status = userExamDetail.getStatus();
            if( eid == examId && examDept.equals(dName)){
                count++;
                if(status == 1) passCount++;
                else if(status == -1) failCount ++;
            }else{
                examStatisticesList.add(new ExamStatistices(examId,exams.get(i-1).geteName(),
                        count,failCount,passCount,100 * Double.parseDouble(df.format((double)passCount/(double)count)),
                        0, exams.get(i-1).getEst(), exams.get(i-1).getEet(),examDept));
                examId = eid;
                examDept = dName;
                count = 1;
                if(status == 1) {passCount = 1;failCount = 0;}
                else if(status == -1) {passCount = 0; failCount = 1;}
                else {passCount = 0; failCount = 0;}
            }
        }
        examStatisticesList.add(new ExamStatistices(userExamDetail.getEid(),userExamDetail.geteName(),
                count,failCount,passCount,100 * Double.parseDouble(df.format((double)passCount/(double)count)),
                0, userExamDetail.getEst(), userExamDetail.getEet(),userExamDetail.getdName()));
        return examStatisticesList;

    }



}
