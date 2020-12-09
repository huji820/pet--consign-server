package com.jxywkj.application.pet.admin;

import com.jxywkj.application.pet.common.utils.StringUtils;
import com.yangwang.sysframework.utils.JsonUtil;
import com.yangwang.sysframework.utils.StringUtil;
import com.yangwang.sysframework.utils.file.FileUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @TODO
 * @Description
 * @Author Administrator
 * @Date 2020-11-08 21:26
 * @Version 1.0
 */
@Controller
@RequestMapping("admin")
public class ExportController {

    /**
     * 数据导出
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("export")
    public String export(String datas, HttpServletResponse response)
            throws Exception {
        datas = datas.replaceAll("\'", "\"");

        FileUtil.append(datas, "d:\\temp.json");

        List<Map<String, Object>> ja = (ArrayList<Map<String, Object>>) JsonUtil
                .formObject(datas);

        // 获取第一个{}
        String[] headers = datas.substring(datas.indexOf("{") + 1,
                datas.indexOf("}")).split(",");
        String[] columns = new String[headers.length];

        for (int i = 0; i < headers.length; i++) {
            columns[i] = headers[i].split(":")[0].replace("'", "");
        }

        String file = System.currentTimeMillis() + ".xls";
        String basePath = "c:\\tmp\\";
        File directory = new File(basePath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        WritableWorkbook book = Workbook.createWorkbook(new File(basePath
                + "\\\\" + file));
        WritableSheet sheet = book.createSheet("sheet", 0);

        for (int i = 0; i < ja.size(); i++) {
            int j = 0;
            for (String header : columns) {
                Map<String, Object> jo = ja.get(i);
                Label label = new Label(j, i, StringUtil.$Str(jo.get(header
                        .replaceAll("\"", ""))));
                j++;
                sheet.addCell(label);
            }
        }
        book.write();
        book.close();

        download(basePath + "\\\\" + file, response);
        return null;
    }

    public HttpServletResponse download(String path,
                                        HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1)
                    .toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            // 另存为和迅雷只主持GK2312
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes("gb2312"), "ISO-8859-1" ));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response
                    .getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public HttpServletResponse downloadAndDelete(String path,
                                                 HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1)
                    .toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            // 另存为和迅雷只主持GK2312
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes("gb2312"), "ISO-8859-1" ));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response
                    .getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            file.delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public void downloadLocal(String toFileName, String filePath, HttpServletResponse response)
            throws FileNotFoundException {
        // 读到流中
        InputStream inStream = new FileInputStream(filePath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\""
                + toFileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //支持在线打开文件的一种方式
    public void downLoad(String filePath, HttpServletResponse response,
                         boolean isOnLine) throws Exception {
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;

        response.reset(); // 非常重要
        if (isOnLine) { // 在线打开方式
            URL u = new URL("file:///" + filePath);
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename="
                    + f.getName());
            // 文件名应该编码成UTF-8
        } else { // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + f.getName());
        }
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0)
            out.write(buf, 0, len);
        br.close();
        out.close();
    }

    public String initAscllFileName(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1)
                .toUpperCase();
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        char[] chars = name.toCharArray();
        StringBuilder newAscllFileName = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                newAscllFileName.append((int) chars[i] + "-");
            } else {
                newAscllFileName.append((int) chars[i]);
            }
        }
        return newAscllFileName.toString()+ "." + ext;
    }

    public String backAscllFileName(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1)
                .toUpperCase();
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String[] chars = name.split("-");
        StringBuilder newAscllFileName = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                newAscllFileName.append((char) Integer.parseInt(chars[i]));
            } else {
                newAscllFileName.append((char) Integer.parseInt(chars[i]));
            }
        }
        return newAscllFileName.toString()+ "." + ext;
    }
}
