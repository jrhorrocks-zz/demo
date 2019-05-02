package com.example.demo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Log4j2
@RestController
@SpringBootApplication
public class DemoApplication {

    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    private FileStorageService fileStorageService;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/message")
    public String printThings(){
        log.info("Printing Things...");

        return "Hello; World!!";
    }

    @GetMapping("/version")
    public String getVersion(){
        log.info("Getting Version...");

        return buildProperties.getVersion();
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/Users/jrhorrocks/DownLoads/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadFilePlus")
    public UploadFileResponse uploadFilePlus(@RequestParam("data") String data,
                                             @RequestParam(value = "file", required = false) MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        DataObj dataObj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            dataObj = mapper.readValue(data, DataObj.class);

        } catch (Exception e) {
            log.error("Something bad happened... [ " + e.getMessage() + " ]");
        }

        log.info("Object... " + dataObj.toString());


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/Users/jrhorrocks/DownLoads/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(), dataObj);
    }

}
