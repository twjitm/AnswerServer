package com.hjq.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

/**
 * import com.itextpdf.text.Document;import com.itextpdf.text.DocumentException;import com.itextpdf.text.pdf.PdfWriter;
 * import com.itextpdf.tool.xml.XMLWorkerHelper;

 * Created by 文江 on 2017/11/30.
 */
public class TestMain {
    public static void main(String[] args) {
        Document document = new Document();
        try {
           String HTML ="C:\\Users\\文江\\Downloads\\ftmp_121_ub\\index.html";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("G:/index.pdf"));
            document.open();
           XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(HTML), Charset.forName("UTF-8"));
           // document.add(new Paragraph("<a>Hello World!</a>"));
            //添加内容
           // document.add(new Paragraph("Some content here"));

            //设置属性
            //标题
            document.addTitle("this is a title");
            //作者
            document.addAuthor("H__D");
            //主题
            document.addSubject("this is subject");
            //关键字
            document.addKeywords("Keywords");
            //创建时间
            document.addCreationDate();
            //应用程序
            document.addCreator("hd.com");

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
