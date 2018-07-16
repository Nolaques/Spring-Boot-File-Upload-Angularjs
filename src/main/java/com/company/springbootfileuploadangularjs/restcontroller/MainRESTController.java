package com.company.springbootfileuploadangularjs.restcontroller;


import com.company.springbootfileuploadangularjs.form.UploadForm;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainRESTController {

    //linux: /home/{user}/test
    //windows: c:/users/{user}/test
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/test";

    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> uploadFileMulti(@ModelAttribute UploadForm form) throws Exception{
        System.out.println("Description: " + form.getDescription());

        String result = null;
        try {
            result = this.saveUploadedFiles(form.getFiles());
        }
        //here catch IOException only
        //other exceptions can be catched by RestGlobalExceptionHandler class
        catch(IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Uploaded to: " + result, HttpStatus.OK);
    }

    //save files
    private String saveUploadedFiles(MultipartFile[] files) throws IOException{
        //make sure directory exists
        File uploadDir = new File(UPLOAD_DIR);
        uploadDir.mkdirs();

        StringBuilder sb = new StringBuilder();

        for (MultipartFile file: files
             ) {
            if (file.isEmpty()){
                continue;
            }

            String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);

            sb.append(uploadFilePath).append("; ");
        }
        return sb.toString();
    }

    @GetMapping("/rest/getAllFiles")
    public List<String> getListFiles(){
        File uploadDir = new File(UPLOAD_DIR);

        File[] files = uploadDir.listFiles();

        List<String> list = new ArrayList<>();
        for (File file: files
             ) {
            list.add(file.getName());
        }
        return list;
    }

    //@filename: abc.zip
    @GetMapping("/rest/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException{
        File file = new File(UPLOAD_DIR + "/"+ filename);
        if (!file.exists()){
            throw new RuntimeException("File not found");
        }

        Resource resource = new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
