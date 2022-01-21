/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.controllers;

import com.ravc.simpleimport.models.ModelCidade;
import com.ravc.simpleimport.models.ModelClassFiscal;
import com.ravc.simpleimport.models.ModelEmpresa;
import com.ravc.simpleimport.models.ModelPessoa;
import com.ravc.simpleimport.models.ModelSitTrib;
import com.ravc.simpleimport.models.ModelUnMed;
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

    public List<ModelPessoa> getPessoas(Connection conn) {
        List<ModelPessoa> pessoas = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement("SELECT * FROM PESSOAS WHERE IDTIPO_PS = 1")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelPessoa pessoa = new ModelPessoa();
                    pessoa.setIdPessoa(rs.getInt("IDPESSOA"));
                    pessoa.setIdTipo_ps(rs.getInt("IDTIPO_PS"));
                    pessoa.setCnpjCpf(rs.getString("CNPJCPF"));
                    pessoa.setIe(rs.getString("INSCR_EST"));
                    pessoas.add(pessoa);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return pessoas;
    }
    
    public List<ModelClassFiscal> getClassFiscal() {
        List<ModelClassFiscal> classfiscal = new ArrayList<>();

        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT IDCLASSFISCAL, CLASSIFICACAO FROM CLASSFISCAL")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelClassFiscal ncm = new ModelClassFiscal();
                    ncm.setId(rs.getString("IDCLASSFISCAL"));
                    ncm.setNcm(rs.getString("CLASSIFICACAO"));
                    classfiscal.add(ncm);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return classfiscal;
    }

    public List<ModelSitTrib> getSitTribs() {
        List<ModelSitTrib> sittribs = new ArrayList<>();

        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT IDSITTRIB, CODIGO, CSOSN FROM SITTRIB")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelSitTrib sittrib = new ModelSitTrib();
                    sittrib.setId(rs.getInt("IDSITTRIB"));
                    sittrib.setCst(rs.getString("CODIGO"));
                    sittrib.setCsosn(rs.getString("CSOSN"));
                    sittribs.add(sittrib);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return sittribs;
    }

    public void saveUnMed(String IDUN, String DESCRICAO) {
        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("INSERT INTO UN (IDUN, DESCRICAO)\n"
                        + "VALUES (?, ?);")) {
            pst.setString(1, IDUN);
            pst.setString(2, DESCRICAO);
            pst.execute();
        } catch (Exception ex) {
            logger.error("Não foi possível gravar uma nova unidade de medida, motivo:");
            logger.error(ExceptionUtils.getStackTrace(ex));
        }
    }

    public List<ModelUnMed> getUnidades() {
        List<ModelUnMed> unidades = new ArrayList<>();

        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT IDUN, DESCRICAO FROM UN ")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    ModelUnMed un = new ModelUnMed();
                    un.setUnMed(rs.getString("IDUN"));
                    un.setDescricao(rs.getString("DESCRICAO"));
                    unidades.add(un);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return unidades;
    }

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

    public int getLastIdProduct(int idEmpresa) {
        int idProduto = 0;
        try (Connection conn = new Database().getConnection();
                PreparedStatement pst = conn.prepareStatement("SELECT MAX(IDPRODUTO) AS IDPRODUTO FROM PRODUTOS "
                        + "WHERE IDEMPRESA = ? ")) {
            pst.setInt(1, idEmpresa);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    idProduto = rs.getInt("IDPRODUTO") + 1;
                }
            }
        } catch (Exception ex) {
            logger.error("Problemas ao obter ultimo IDPRODUTO, motivo:");
            logger.error(ExceptionUtils.getStackTrace(ex));
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return idProduto;
    }
}
