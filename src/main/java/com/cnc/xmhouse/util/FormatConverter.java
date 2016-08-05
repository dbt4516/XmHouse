package com.cnc.xmhouse.util;

import com.cnc.xmhouse.lagacy_entity.UserExamQuestion;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hongzhan on 2016/7/27.
 */
public class FormatConverter {

    static public List<Long> stringArray2LongList(String[] src) {
        List<Long> qids = new LinkedList<>();
        if (src != null) {
            for (String s : src) {
                qids.add(Long.valueOf(s));
            }
        }
        return qids;
    }

    static public List<Integer> stringArray2IntList(String[] src) {
        List<Integer> qids = new LinkedList<>();
        if (src != null) {
            for (String s : src) {
                qids.add(Integer.valueOf(s));
            }
        }
        return qids;
    }

    static public List<String> stringArray2StringList(String[] src) {
        return Arrays.stream(src).filter(s -> s!=null).collect(Collectors.toList());
    }

    static public List<UserExamQuestion>stringArray2UEQList(String[] src){
        List<UserExamQuestion> ueqs = new LinkedList<>();
        Gson GSON=new Gson();
        if (src != null) {
            for (String s : src) {
                ueqs.add(GSON.fromJson(s,UserExamQuestion.class));
            }
        }
        return ueqs;
    }
}
