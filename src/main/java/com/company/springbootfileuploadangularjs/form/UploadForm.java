package com.company.springbootfileuploadangularjs.form;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

    private String description;

    private MultipartFile[] files;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
