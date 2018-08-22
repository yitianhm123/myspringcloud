package com.estate.util;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class myBatisGenerator {

    public void generator() throws Exception{


        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //加载配置文件 :D:\man\project\feature_gyc_mg_505_61_2017117\91steel.com.trade.common\generatorConfig.xml
        File configFile = new File("eurekaServer/src/main/resources/generator/generatorConfig.xml");
//        File testFile = new File("classpath:./eurekaServer/a");
        System.out.println(configFile.getAbsolutePath());
//        System.out.println(testFile.getAbsolutePath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);


    }
    public static void main(String[] args) throws Exception {
        try {
            myBatisGenerator generatorSqlmap = new myBatisGenerator();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
