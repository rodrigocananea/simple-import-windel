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
public class ModelEmpresa {

    int id;
    String nome;
    int idCidade;

    public ModelEmpresa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }
    
    @Override
    public String toString() {
        return " (" + id + ") " + nome.toUpperCase();
    }

}
