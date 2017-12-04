package com.hjq.answer.controller;

import com.alibaba.fastjson.JSON;
import com.hjq.answer.entity.*;
import com.hjq.answer.enums.Qtypes;
import com.hjq.answer.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 文江 on 2017/12/3.
 */
@RequestMapping("/answer")
@Controller
public class AnswerContorller {

    @Resource
    public AnswerService answerService;

    @RequestMapping("plist")
    public String paperList(HttpServletRequest request) {
        request.setAttribute("plist", answerService.getallPapers());
        return "/plist";
    }

    @RequestMapping("elist")
    public String explainList(HttpServletRequest request) {
        List<Choices> choices = answerService.getAllChoices();
        List<Explain> explains = answerService.getAllExplain();
        request.setAttribute("choices", choices);
        request.setAttribute("explains", explains);
        return "/elist";
    }

    public String delete(HttpServletRequest request, Integer id) {
        answerService.deletePaper(id);
        return "redirect:/answer/plist.do";
    }

    @RequestMapping("addsubject")
    public String addSubject(HttpServletRequest request, SubjectVo subjectVo) {
        int type = subjectVo.getQtype();
        String json = JSON.toJSONString(subjectVo);
        if (type == Qtypes.TYPE_CHOICES.getValue()) {
            Choices choices = JSON.parseObject(json, Choices.class);
            answerService.addChoices(choices);
        } else {
            Explain explain = JSON.parseObject(json, Explain.class);
            answerService.addExplain(explain);
        }
        return "redirect:/answer/elist.do";
    }

}
