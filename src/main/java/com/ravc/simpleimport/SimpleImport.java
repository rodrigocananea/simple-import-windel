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
import com.ravc.simpleimport.views.SplashScreen;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Rodrigo
 */
public class SimpleImport {

    public static SimpleImportProp SIProp;

    public static void main(String[] args) throws InterruptedException {
        SIProp = new SimpleImportProp();
        String theme = SIProp.prop().getString("tema", "Dark");

        switch (theme) {
            case "Dark":
                FlatOneDarkIJTheme.install();
                break;
            case "Light":
                FlatIntelliJLaf.install();
                break;
            default:
                FlatOneDarkIJTheme.install();
                break;
        }

        SplashScreen screen = new SplashScreen();
        screen.setVisible(true);
        Thread.sleep(1000);
        validateLocalCfg(screen);
        java.awt.EventQueue.invokeLater(() -> {
            screen.jlStatus.setText("Carregando tela principal, aguarde...");
            Main main = new Main();
            screen.jlStatus.setText("Abrindo janelas, aguarde...");
            screen.setVisible(false);
            main.setVisible(true);
        });
    }

    public static void validateLocalCfg(SplashScreen screen) throws InterruptedException {
        screen.jlStatus.setText("Verificando arquivo LOCAL.cfg, aguarde...");
        Thread.sleep(1000);
        File local = new File(System.getProperty("user.dir") + File.separator + "LOCAL.cfg");
        System.out.println(local.getPath());
        if (local.exists()) {
            try (Scanner sc = new Scanner(local)) {
                String line = sc.nextLine();
                String[] parts = line.split(":");
                if (parts.length >= 3) {
                    screen.jlStatus.setText("Atualizando informações com LOCAL.cfg, aguarde...");
                    SIProp.setProp("host.name", parts[0]);
                    SIProp.setProp("host.database", parts[1] + ":" + parts[2]);
                    Thread.sleep(500);
                    screen.jlStatus.setText("LOCAL.cfg carregado!");
                }
            } catch (FileNotFoundException ex) {
            }
        } else {
            screen.jlStatus.setText("LOCAL.cfg não existe!");
        }
    }

}
