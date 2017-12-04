package com.hjq.answer.service.Impl;

import com.hjq.answer.dao.ChoicesMapper;
import com.hjq.answer.dao.ExplainMapper;
import com.hjq.answer.entity.AnswerVo;
import com.hjq.answer.entity.Choices;
import com.hjq.answer.entity.Explain;
import com.hjq.answer.enums.Qtypes;
import com.hjq.answer.service.AnswerService;
import com.hjq.answer.service.QtypesService;
import com.hjq.answer.dao.PapersMapper;
import com.hjq.answer.entity.Papers;
import com.hjq.utils.HtmlUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 文江 on 2017/11/30.
 */
public class AnswerServiceImpl implements AnswerService {
    public QtypesService qtypesService;
    public ExplainMapper explainMapper;
    public ChoicesMapper choicesMapper;
    public PapersMapper papersMapper;

    public void addExplain(Explain exception) {
        explainMapper.insert(exception);
    }

    public void addChoices(Choices choices) {
        choicesMapper.insert(choices);
    }

    public void deleteAnwer(int id, int type) {
        if (type == Qtypes.TYPE_CHOICES.getValue()) {
            choicesMapper.deleteByPrimaryKey(id);
        } else {
            explainMapper.deleteByPrimaryKey(id);
        }
    }

    public void updateAnwer(Object object, int type) {

    }

    public List<Explain> getAllExceptionBytype(int type) {
        return explainMapper.getAllExceptionBytype(type);
    }

    public Explain getExceptionById(int id) {
        Explain result = explainMapper.selectByPrimaryKey(id);
        return result;
    }

    public Explain getExceptionById(int id, int type) {
        return explainMapper.getExceptionById(id, type);
    }

    public List<Choices> getAllChoices() {
        return choicesMapper.getAllChose();
    }

    public Choices getChoicesById(int id) {
        return choicesMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Papers> getAllPapers() {
        return null;
    }

    @Override
    public void addPapers(Papers papers) {

    }

    /**
     * 试卷生成
     * @return
     */

    @Override
    public boolean combination(String title, List<AnswerVo> answerVos) {
        String setverPath = "//";
        String fileType = ".html";
        if (answerVos == null || answerVos.size() == 0) return false;
        //排序标号
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<!doctype html>");
        stringBuffer.append(HtmlUtils.getHead(title));
        stringBuffer.append("<body>");
        stringBuffer.append("<div>");
        stringBuffer.append(HtmlUtils.getStyle());
        stringBuffer.append("<header class=\"wrap-header\">\n" +
                "      <article>\n" +
                "        <div class=\"wrap-title\" >\n" +
                "          <h1>" +
                "<span>" +
                "河北大学 2018 " +
                title +
                " 期末考试" +
                "</span></h1>\n" +
                "          <p>考试时间:2018年12月1日</p>\n" +
                "        </div>\n" +
                "      </article>\n" +
                "    </header>");
        stringBuffer.append(" <div class=\"row row-info\">");
        for (int i = 0; i < answerVos.size(); i++) {
            int answerType = answerVos.get(i).getType();
            stringBuffer.append("<article class=\"markup\">" +
                    "  <h2 class=\"section-title\">" +
                    "" +
                    i +
                    Qtypes.getTitle(answerType) +(answerVos.get(i).getScore())+
                    "</h2>"
            );
            //处理选择题
            if (answerType == Qtypes.TYPE_CHOICES.getValue()) {
                List<Choices> choicesList = this.getAllChoices();
                List<Integer> idList=new ArrayList<Integer>();
                for(int j=0;j<choicesList.size();j++){
                    idList.add(choicesList.get(j).getId());
                }
                int[] finalIdarray=getRandomArray(answerVos.get(i).getNumber(),idList);
                for (int j=0;j<finalIdarray.length;j++){
                    stringBuffer.append("<p>");
                    Choices choices=choicesMapper.selectByPrimaryKey(finalIdarray[i]);
                    stringBuffer.append("第"+j+"题:"+choices.getTitle());
                    stringBuffer.append("</p>");
                    stringBuffer.append("<blockquote>");
                    String answers=choices.getAnswer();
                    String [] anser=answers.split("#");
                    for(int m=0;m<anser.length;m++){
                        stringBuffer.append(anser[m]);
                    }
                    stringBuffer.append(" </blockquote>");
                }
                stringBuffer.append("</article>");
            }
            //非选着题
            String BagTitle=Qtypes.getTitle(answerType);
            List<Explain> allexceptionType = this.getAllExceptionBytype(answerType);
            List<Integer> idList=new ArrayList<Integer>();
            for(int j=0;j<allexceptionType.size();j++){
                idList.add(allexceptionType.get(j).getId());
            }
            int[] lastAnsuwerIdlist = this.getRandomArray(answerVos.get(i).getNumber(), idList);
           for(int j=0;j<lastAnsuwerIdlist.length;j++){
               Explain explain=this.getExceptionById(lastAnsuwerIdlist[j]);
               //拼接格式了
           }
        }
        stringBuffer.append("\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>");
        boolean success = false;
        File file = new File(setverPath + fileType + fileType);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringBuffer.toString());
            bufferedWriter.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private int[] getRandomArray(int length, List<Integer> region) {

        int[] array = new int[length];
        List<Integer> list = new ArrayList<Integer>();
        Random random = new Random();
        while (list.size() < length) {
            int randomInt = random.nextInt(region.size());
            if(!list.contains(randomInt)){
                list.add(randomInt);
            }
        }
        for(int i=0;i<list.size();i++){
            array[i]=region.get(list.get(i));
        }
        return array;
    }

    @Override
    public List<Papers> getallPapers() {

        return papersMapper.getAllPapers();
    }

    @Override
    public void deletePaper(int id) {
        papersMapper.deleteByPrimaryKey(id);
    }


}
