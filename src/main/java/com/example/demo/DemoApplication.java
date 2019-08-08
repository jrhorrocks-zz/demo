package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.LocalDateTime;

@Log4j2
@RestController
@SpringBootApplication
public class DemoApplication {

    @Autowired
    private BuildProperties buildProperties;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String info() {
        log.info("Gathering Information...");
        InetAddress inetAddress;
        StringBuilder sb = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();

        try {
            inetAddress = InetAddress.getLocalHost();
            sb.append("IP: " + inetAddress);
            sb.append("</br>");
            sb.append("Host: " + inetAddress.getHostName());
            sb.append("</br>");
            sb.append(now);
        } catch (Exception e) {
            log.error("Something bad happened..." + e.getMessage());
        }
        return sb.toString();
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

}
