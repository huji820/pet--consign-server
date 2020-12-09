package com.jxywkj.application.pet.common.enums;

/**
 * @ClassName MediaFileTypeEnum
 * @Description 媒体类型枚举
 * @Author LiuXiangLin
 * @Date 2019/7/22 16:26
 * @Version 1.0
 **/

public enum MediaFileTypeEnum {
    /**
     * 图片
     */
    JPG("图片", "jpg"),
    JPEG("图片", "jpeg"),
    PNG("图片", "png"),
    HEIC("图片", "heic"),
    BMP("图片", "bmp"),
    JP2("图片", "jp2"),
    GIF("图片", "gif"),
    TIFF("图片", "tiff"),
    EXIF("图片", "exif"),
    WBMP("图片", "wbmp"),
    MBM("图片", "mbm"),
    /**
     * 视频
     */
    MP4("视频", "mp4"),
    MOV("视频", "mov"),
    FLV("视频", "flv"),
    WEBM("视频", "webm"),
    M4V("视频", "m4v"),
    THREE_GP("视频", "3gp"),
    THREE_G_TWO("视频", "3g2"),
    RM("视频", "rm"),
    RMVB("视频", "rmvb"),
    WMV("视频", "wmv"),
    AVI("视频", "avi"),
    ASF("视频", "asf"),
    MPG("视频", "mpg"),
    MPEG("视频", "mpeg"),
    MPE("视频", "mpe"),
    TS("视频", "ts"),
    DIV("视频", "div"),
    DV("视频", "dv"),
    DIVX("视频", "divx"),
    VOB("视频", "vob"),
    DAT("视频", "dat"),
    MKV("视频", "mkv"),
    SWF("视频", "swf"),
    LAVF("视频", "lavf"),
    CPK("视频", "cpk"),
    DIRAC("视频", "dirac"),
    RAM("视频", "ram"),
    QT("视频", "qt"),
    FLI("视频", "fli"),
    FLC("视频", "flc"),
    MOD("视频", "mod"),
    /**
     * 音频
     */
    MP3("音频", "mp3");

    String typeName;
    String suffix;

    MediaFileTypeEnum(String typeName, String suffix) {
        this.typeName = typeName;
        this.suffix = suffix;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getSuffix() {
        return suffix;
    }

    public static MediaFileTypeEnum filter(String suffix) {
        for (MediaFileTypeEnum mediaFileTypeEnum : values()) {
            if (mediaFileTypeEnum.getSuffix().equals(suffix.toLowerCase())) {
                return mediaFileTypeEnum;
            }
        }
        return null;
    }
}
