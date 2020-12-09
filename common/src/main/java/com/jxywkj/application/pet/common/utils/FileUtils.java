package com.jxywkj.application.pet.common.utils;

import com.jxywkj.application.pet.common.enums.MediaFileTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @ClassName FileUtils
 * @Description 文件工具类
 * @Author LiuXiangLin
 * @Date 2019/7/22 15:27
 * @Version 1.0
 **/
@Component
public class FileUtils {
    @Value("${file.upload-Path}")
    String BaseFilePath;

    private static final String DEFAULT_ENCODING = "UTF-8";
    public static final String TEMP_FILE_FOLDER = "temp";


    /***
     * <p>
     * deprecated
     * </p>
     *
     * @see AliOssObjectUtils#uploadImg
     * @return com.jxywkj.application.pet.common.utils.FileUpLoadState
     * @author LiuXiangLin
     * @date 14:07 2019/12/17
     **/
    @Deprecated
    public FileUpLoadState fileUpload(MultipartFile multipartFile, String orderNo, MediaFileTypeEnum mediaFileTypeEnum) throws IOException {
        FileUpLoadState result;

        /*文件判断*/
        // 如果原文件不存在则抛出异常
        if (multipartFile.getName().isEmpty() || orderNo.isEmpty()) {
            throw new RuntimeException("读取文件失败！");
        }

        /*创建文件夹*/
        File fileDir = new File(BaseFilePath + orderNo);
        if (!fileDir.exists()) {
            if (!fileDir.mkdir()) {
                throw new RuntimeException("创建文件路径失败！");
            }
        }

        /*写入文件*/
        // 获取新文件名
        String newFileName = getNewFileName(multipartFile.getOriginalFilename());
        // 创建文件对象
        File writeInFile = new File(BaseFilePath + orderNo + "/" + newFileName);
        // 写入文件
        multipartFile.transferTo(writeInFile);

        /*返回数据*/
        result = new FileUpLoadState();
        result.setFileAddress(orderNo + "/" + newFileName);
        result.setNewFileName(newFileName);
        result.setState(FileUpLoadState.SUCCESS);
        result.setMediaFileTypeEnum(mediaFileTypeEnum);

        return result;
    }

    /**
     * @return java.lang.String
     * @Author LiuXiangLin
     * @Description 获取新问文件名称
     * @Date 15:50 2019/7/22
     * @Param []
     **/
    String getNewFileName(String oldFileName) {
        return System.currentTimeMillis() + "-" + StringUtils.getUuid() + "." + getFileSuffix(oldFileName);
    }

    /**
     * @return java.lang.String
     * @Author LiuXiangLin
     * @Description 获取文件后缀名
     * @Date 15:52 2019/7/22
     * @Param [fileName]
     **/
    public String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }


    /**
     * @return boolean
     * @Author LiuXiangLin
     * @Description 删除一个订单文件夹下的所有文件 包括文件夹
     * @Date 14:23 2019/8/13
     * @Param [orderNo]
     **/
    public boolean deletOrderFolder(String orderNo) {
        File file = new File(BaseFilePath + "/" + orderNo);
        if (file.exists() && file.isDirectory()) {
            return file.delete();
        }
        return false;
    }

    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 删除指定文件夹下的文件
     * @Date 14:31 2019/8/13
     * @Param [orderNo, filePathArray]
     **/
    public int deleteOrderFolderFils(String orderNo, String[] filePathArray) {
        int result = 0;
        File folder = new File(BaseFilePath + "/" + orderNo);
        File deleteFile;
        if (folder.exists() && folder.isDirectory()) {
            for (String filePath : filePathArray) {
                deleteFile = new File(BaseFilePath + "/" + filePath);
                if (deleteFile.exists() && !deleteFile.isDirectory()) {
                    result = deleteFile.delete() ? result + 1 : result;
                }
            }
        }
        return result;
    }

    /**
     * <p>
     * 获取指定文件的值
     * </p>
     *
     * @param file 文件对象)
     * @return java.lang.String
     * @author LiuXiangLin
     * @date 9:23 2019/11/12
     **/
    public String getFileValues(File file) {
        Long fileLength = file.length();
        byte[] fileByte = new byte[fileLength.intValue()];

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileByte);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return new String(fileByte, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * @return
     * @description 图片地址移动到指定文件夹下
     * @author lsy
     * @date 10:57 2019/11/29
     **/
    public String moveFile(String shopIndoorImg, String businessNo) {
        if (shopIndoorImg == null) {
            return null;
        }

        File oldFile = new File(BaseFilePath + TEMP_FILE_FOLDER + "/" + shopIndoorImg);
        File newFile = new File(BaseFilePath + businessNo);

        if (!newFile.exists()) {
            newFile.mkdirs();
        }

        File newFileDir = new File(BaseFilePath + businessNo + "/" + shopIndoorImg);

        oldFile.renameTo(newFileDir);

        return newFileDir.toString();
    }
}
