/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ravc.simpleimport.utils;

import static com.ravc.simpleimport.SimpleImport.SIProp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private String url;
    private String userName;
    private String password;
    private Connection connection;

    public Database() throws SQLException, ClassNotFoundException {
        this.url = "jdbc:firebirdsql:local:" 
                + SIProp.prop().getString("host.database")
                + SIProp.prop().getString("host.params", "");
        this.userName = SIProp.prop().getString("host.user", "SYSDBA");
        this.password = SIProp.prop().getString("host.password", "masterkey");
        setConnection();
    }
    
    public Database(String database) throws SQLException, ClassNotFoundException {
        this.url = "jdbc:firebirdsql:local:" 
                + database
                + SIProp.prop().getString("host.params", "");
        this.userName = SIProp.prop().getString("host.user", "SYSDBA");
        this.password = SIProp.prop().getString("host.password", "masterkey");
        setConnection();
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void setConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.firebirdsql.jdbc.FBDriver"); 
        Properties props = new Properties();

        props.setProperty("user", userName);
        props.setProperty("password", password);
        props.setProperty("encoding", "UTF8");
        connection = DriverManager.getConnection(url, props);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

}
