/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Rodrigo
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        String sDate1="31/12/1998";  
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
        System.out.println("# " + sdf.format(new Date()));  
        
    }
}
