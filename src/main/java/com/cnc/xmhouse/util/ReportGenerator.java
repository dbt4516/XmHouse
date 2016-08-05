package com.cnc.xmhouse.util;

import com.cnc.xmhouse.lagacy_entity.UserExamDetail;
import com.cnc.xmhouse.enums.UserExamStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hongzhan on 2016/8/1.
 */
public class ReportGenerator {

    public static String gen(List<UserExamDetail> list) {
        return list.stream().map(l ->
                new StringBuffer().append(l.getuName() + ",").append(l.getMail() + ",").append(l.getdName() + ",")
                        .append(l.getcName() + ",").append((l.getScore() != null ? l.getScore() : "") + ",").append(UserExamStatus.statusName(l.getStatus()) + ",")
                        .append(l.getUst() != null ? new SimpleDateFormat("yyyy-MM-dd").format(new Date(l.getUst().getTime())) : "")
        ).reduce(new StringBuffer("姓名,邮箱,部门,课程,成绩,通过,考试时间"), (a, b) -> a.append("\n").append(b)).toString();
    }
}
