package com.javaex.vo;

import java.io.InputStream;

public class FileVo {
    private String fileName;
    private Long fileSize;
    private InputStream fileContent;

    public FileVo(String fileName, Long fileSize, InputStream fileContent) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
    }


    public String getFileName() {
        return fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public InputStream getFileContent() {
        return fileContent;
    }
}
