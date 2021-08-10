/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.utils;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Rodrigo
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        
        String txt = "1.0";
        System.out.println("# " + StringUtils.isNumeric(txt));  
        
    }
}
