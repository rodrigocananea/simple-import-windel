/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.controllers;

import com.ravc.simpleimport.models.ModelCidade;
import com.ravc.simpleimport.models.ModelEmpresa;
import com.ravc.simpleimport.utils.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Rodrigo
 */
public class ControllerData {

    final static Logger logger = Logger.getLogger("");
    
    public List<ModelEmpresa> getEmpresas() {
        List<ModelEmpresa> empresas = new ArrayList<>();

        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT IDEMPRESA, NOME, CIDADE FROM EMPRESAS")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelEmpresa empresa = new ModelEmpresa();
                    empresa.setId(rs.getInt("IDEMPRESA"));
                    empresa.setNome(rs.getString("NOME"));
                    empresa.setIdCidade(rs.getInt("CIDADE"));
                    empresas.add(empresa);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return empresas;
    }

    public List<ModelCidade> getCidades() {
        List<ModelCidade> cidades = new ArrayList<>();

        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT IDCIDADE, DESCRICAO, UF, COD_NACIONAL FROM CIDADES")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelCidade cidade = new ModelCidade();
                    cidade.setId(rs.getInt("IDCIDADE"));
                    cidade.setNome(rs.getString("DESCRICAO"));
                    cidade.setUF(rs.getString("UF"));
                    cidade.setIbge(rs.getInt("COD_NACIONAL"));
                    cidades.add(cidade);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return cidades;
    }

    public int getLastIdClient(int idEmpresa) {
        int idPessoa = 0;
        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT MAX(IDPESSOA)+1 AS LASTID FROM PESSOAS"
                        + " WHERE IDTIPO_PS = 1 AND IDEMPRESA = ?")) {
            pst.setInt(1, idEmpresa);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    idPessoa = rs.getInt("LASTID");
                }
            }
        } catch (Exception ex) {
            logger.error("Problemas ao obter ultimo IDPESSOA, motivo:");
            logger.error(ExceptionUtils.getStackTrace(ex));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return idPessoa;
    }
}
