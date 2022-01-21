/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.utils;

import static com.ravc.simpleimport.SimpleImport.SIProp;
import com.ravc.simpleimport.controllers.ControllerData;
import com.ravc.simpleimport.models.ModelPessoa;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rodrigo
 */
public class Test {

    public static void main(String[] args) throws Exception {
        SIProp = new SimpleImportProp();

        ControllerData controller = new ControllerData();

        Connection marino = new Database("D:\\WINDEL\\DADOS\\Marino\\SISTEMA.FDB").getConnection();
        Connection ariel = new Database("D:\\WINDEL\\DADOS\\Ariel\\SISTEMA.FDB").getConnection();

        List<ModelPessoa> pessoasAriel = controller.getPessoas(ariel);
        List<ModelPessoa> pessoasMarino = controller.getPessoas(marino);
        List<Integer> naoCadastrados = new ArrayList<>();

        System.out.println("COUNT : " + pessoasMarino.size() + " - MARINO");
        System.out.println("COUNT : " + pessoasAriel.size() + " - ARIEL");

        for (ModelPessoa a : pessoasAriel) {
            boolean existe = pessoasMarino.stream()
                    .filter(m -> {
                        String cnpjCpf = m.getCnpjCpf();
                        String ie = m.getIe();
                        String cnpjCpfAriel = a.getCnpjCpf();
                        String ieAriel = a.getIe();

                        if (cnpjCpf == null) {
                            cnpjCpf = "";
                        }
                        if (ie == null) {
                            ie = "";
                        }
                        if (cnpjCpfAriel == null) {
                            cnpjCpfAriel = "";
                        }
                        if (ieAriel == null) {
                            ieAriel = "";
                        }

                        cnpjCpf = cnpjCpf.replaceAll("\\D", "");
                        cnpjCpfAriel = cnpjCpfAriel.replaceAll("\\D", "");
                        ie = ie.replaceAll("\\D", "");
                        ieAriel = ieAriel.replaceAll("\\D", "");
                        return cnpjCpf.equals(cnpjCpfAriel) && ie.equals(ieAriel);
                    })
                    .findFirst().isPresent();

            if (!existe) {
                naoCadastrados.add(a.getIdPessoa());
            }
        }

        System.out.println(": NÃO CADASTRADOS : COUNT > " + naoCadastrados.size());
        String txt = naoCadastrados.stream().sorted().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(txt);
    }
}
