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

public class Database {

    private String url;
    private String userName;
    private String password;
    private Connection connection;

    public Database() throws SQLException {
        this.url = "jdbc:firebirdsql:native:"
                + SIProp.prop().getString("host.name", "localhost") + "/" + SIProp.prop().getString("host.port", "3050")
                + ":" + SIProp.prop().getString("host.database")
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

    private void setConnection() throws SQLException {
        connection = DriverManager.getConnection(url, userName, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

}
