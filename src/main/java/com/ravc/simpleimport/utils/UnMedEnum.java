/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.utils;

/**
 *
 * @author Rodrigo
 */
public enum UnMedEnum {

    AMPOLA("AMPOLA", "AMPOLA"),
    BALDE("BALDE", "BALDE"),
    BANDEJ("BANDEJ", "BANDEJA"),
    BARRA("BARRA", "BARRA"),
    BISNAG("BISNAG", "BISNAGA"),
    BLOCO("BLOCO", "BLOCO"),
    BOBINA("BOBINA", "BOBINA"),
    BOMBEAR("BOMBEAR", ""),
    CÁPSULAS("CÁPSULAS", "CÁPSULA"),
    CARRINHO("CARRINHO", "CARTELA"),
    CENTO("CENTO", "CENTO"),
    CJ("CJ", "CONJUNTO"),
    CM("CM", "CENTÍMETRO"),
    CM2("CM2", " QUADRADO"),
    CX("CX", "CAIXA"),
    CX2("CX2", " COM 2 UNIDADES"),
    CX3("CX3", " COM 3 UNIDADES"),
    CX5("CX5", " COM 5 UNIDADES"),
    CX10("CX10", "CAIXA COM 10 UNIDADES"),
    CX15("CX15", "CAIXA COM 15 UNIDADES"),
    CX20("CX20", "CAIXA COM 20 UNIDADES"),
    CX25("CX25", "CAIXA COM 25 UNIDADES"),
    CX50("CX50", "CAIXA COM 50 UNIDADES"),
    CX100("CX100", "CAIXA COM 100 UNIDADES"),
    DISP("DISP", "EXIBIÇÃO"),
    DUZIA("DUZIA", "DUZIA"),
    EMBAL("EMBAL", "EMBALAGEM"),
    FARDO("FARDO", "FARDO"),
    FOLHA("FOLHA", "FOLHA"),
    FRASCO("FRASCO", "FRASCO"),
    GALAO("GALAO", "GALÃO"),
    GL("GL", "GALÃO"),
    GF("GF", "GARRAFA"),
    GRAMAS("GRAMAS", "GRAMAS"),
    JOGO("JOGO", "JOGO"),
    KG("KG", "QUILOGRAMA"),
    KIT("KIT", ""),
    LATA("LATA", "LATA"),
    LITRO("LITRO", "LITRO"),
    L("L", "LITRO"),
    M("M", "METRO"),
    M2("M2", "METRO QUADRADO"),
    M3("M3", "METRO CÚBICO"),
    MILHEI("MILHEI", "MILHEIRO"),
    ML("ML", "MILILITRO"),
    MWH("MWH", " HORA"),
    PACOTE("PACOTE", "PACOTE"),
    PALETE("PALETE", "PALETE"),
    PARES("PARES", "PARES"),
    PC("PC", "PEÇA"),
    AMIGO("AMIGO", "AMIGO"),
    K("K", "QUILATE"),
    RESMA("RESMA", "RESMA"),
    ROLO("ROLO", "ROLO"),
    SC("SC", "SACO"),
    SACO("SACO", "SACO"),
    SACOLA("SACOLA", "SACOLA"),
    TAMBOR("TAMBOR", "TAMBOR"),
    TANQUE("TANQUE", "TANQUE"),
    TON("TON", ""),
    TUBO("TUBO", "TUBO"),
    UN("UN", "UNIDADE"),
    UNID("UNID", "UNIDADE"),
    VASIL("VASIL", "VASILHAME"),
    VIDRO("VIDRO", "VIDRO");

    private final String unMed;
    private final String nome;

    UnMedEnum(final String unMed, final String nome) {
        this.unMed = unMed;
        this.nome = nome;
    }

    public String getunMed() {
        return unMed;
    }

    public String getNome() {
        return nome;
    }

}
