/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.utils;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;


/**
 *
 * @author Rodrigo
 */
public class SimpleImportProp {

    final static Logger logger = Logger.getLogger("utils/SIProp");
    private Parameters params = new Parameters();
    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    public SimpleImportProp() {
        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.fileBased()
                        .setFile(new File("simpleimport.properties")));
        builder.setAutoSave(true);

        File fileProp = new File(System.getProperty("user.dir") + File.separator + "simpleimport.properties");

        if (!fileProp.exists()) {
            try (FileOutputStream fileOut = new FileOutputStream(fileProp)) {
                Configuration config = builder.getConfiguration();
                config.setProperty("host.name", "localhost");
                config.setProperty("host.port", "3050");
                config.setProperty("host.user", "SYSDBA");
                config.setProperty("host.database", "cnfe");
            } catch (Exception ex) {
                logger.error("Erro ao recriar arquivos de propriedades, motivo:");
                logger.error(ExceptionUtils.getStackTrace(ex));
            }
        }
    }

    public Configuration prop() {
        try {
            return builder.getConfiguration();
        } catch (ConfigurationException cex) {
            logger.error("Erro ao setar propriedades, motivo:");
            logger.error(ExceptionUtils.getStackTrace(cex));
        }
        return null;
    }

    public void setProp(String prop, String propValue) {
        try {
            Configuration config = builder.getConfiguration();
            config.setProperty(prop, propValue);
        } catch (ConfigurationException cex) {
            logger.error("Erro ao setar propriedades, motivo:");
            logger.error(ExceptionUtils.getStackTrace(cex));
        }
    }

}
