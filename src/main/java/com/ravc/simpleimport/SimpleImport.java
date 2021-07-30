/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.ravc.simpleimport.utils.SimpleImportProp;
import com.ravc.simpleimport.views.Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Rodrigo
 */
public class SimpleImport {

    public static SimpleImportProp SIProp;

    public static void main(String[] args) {
        SIProp = new SimpleImportProp();

        if (SIProp.prop().getString("tema", "Dark").equals("Dark")) {
            FlatOneDarkIJTheme.install();
        } else {
            FlatIntelliJLaf.install();
        }

        validateLocalCfg();
        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    public static void validateLocalCfg() {
        File local = new File(System.getProperty("user.dir") + File.separator + "LOCAL.cfg");
        System.out.println(local.getPath());
        if (local.exists()) {
            try (Scanner sc = new Scanner(local)) {
                String line = sc.nextLine();
                String[] parts = line.split(":");
                if (parts.length >= 3) {
                    System.out.println("Ajustando informações com o LOCAL.cfg");
                    SIProp.setProp("host.name", parts[0]);
                    SIProp.setProp("host.database", parts[1] + ":" + parts[2]);
                }
            } catch (FileNotFoundException ex) {
            }
        }
    }

}
