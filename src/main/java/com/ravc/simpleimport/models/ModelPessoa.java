/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.models;

/**
 *
 * @author Rodrigo
 */
public class ModelPessoa {
    
    private int idPessoa;
    private int idTipo_ps;
    private String cnpjCpf;
    private String ie;

    public ModelPessoa() {
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public int getIdTipo_ps() {
        return idTipo_ps;
    }

    public void setIdTipo_ps(int idTipo_ps) {
        this.idTipo_ps = idTipo_ps;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    @Override
    public String toString() {
        return "ModelPessoa{" + "idPessoa=" + idPessoa + ", idTipo_ps=" + idTipo_ps + ", cnpjCpf=" + cnpjCpf + ", ie=" + ie + '}';
    }    
}
