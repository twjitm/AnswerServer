package com.hjq.answer.service.Impl;

import com.hjq.answer.dao.ChoicesMapper;
import com.hjq.answer.dao.ExplainMapper;
import com.hjq.answer.entity.AnswerVo;
import com.hjq.answer.entity.Choices;
import com.hjq.answer.entity.Explain;
import com.hjq.answer.enums.Qtypes;
import com.hjq.answer.service.AnswerService;
import com.hjq.answer.service.QtypesService;
import com.hjq.papers.dao.PapersMapper;
import com.hjq.papers.entity.Papers;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;
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

    @Override
    public boolean combination(String title, List<AnswerVo> answerVos) {
        String setverPath = "//";
        String fileType = ".html";
        String pdfFileType = ".pdf";
        String context = "";
        if (answerVos == null || answerVos.size() == 0) return false;
        //排序标号
        for (int i = 0; i < answerVos.size(); i++) {
            int answerType = answerVos.get(i).getType();
            //处理选择题
            if (answerType == Qtypes.TYPE_CHOICES.getValue()) {
                List<Choices> choicesList = this.getAllChoices();
                List<Integer> idList=new ArrayList<Integer>();
                for(int j=0;j<choicesList.size();j++){
                    idList.add(choicesList.get(j).getId());
                }
                int[] finalIdarray=getRandomArray(answerVos.get(i).getNumber(),idList);
                for (int j=0;j<finalIdarray.length;j++){
                    Choices choices=choicesMapper.selectByPrimaryKey(finalIdarray[i]);
                    //拼接格式啦
                }
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
        boolean success = false;
        File file = new File(setverPath + fileType + fileType);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(context);
            bufferedWriter.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (success) {
                success = false;
                String xmlFile = file.getPath();
                Document document = new Document();
                try {
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(setverPath + title + pdfFileType));
                    document.open();
                    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(xmlFile), Charset.forName("UTF-8"));
                    document.addTitle(title);
                    document.addAuthor("hjq");
                    document.addCreationDate();
                    document.close();
                    writer.close();
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (success) {
                        Papers papers = new Papers();
                        papers.setTitle(title);
                        papers.setUrl(xmlFile);
                        this.addPapers(papers);
                    }
                }
            }
        }
        return false;
    }

    private int[] getRandomArray(int length, List<Integer> region) {
       Thread  thread=new Thread();

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
}
