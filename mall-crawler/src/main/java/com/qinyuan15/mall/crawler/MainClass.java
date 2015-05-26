package com.qinyuan15.mall.crawler;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

/**
 * Main class
 * Created by qinyuan on 15-5-25.
 */
public class MainClass {
    private static void printUsage() {
        System.out.println("Usage: java -jar mall-crawler.jar <string_config_file>");
        System.out.println("spring config file must end with '.xml'");
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            printUsage();
            return;
        }

        String springConfigFile = args[0];
        if (!springConfigFile.endsWith(".xml")) {
            printUsage();
            return;
        }

        if (new File(springConfigFile).isFile()) {
            new FileSystemXmlApplicationContext(springConfigFile);
        } else {
            new ClassPathXmlApplicationContext(springConfigFile);
        }
    }
}
