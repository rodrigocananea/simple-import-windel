package com.ravc.simpleimport.utils;

import static com.ravc.simpleimport.SimpleImport.SIProp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class EDB {

    private String url;
    private String userName;
    private String password;
    private Connection connection;
    final static Logger logger = Logger.getLogger("utils/EDB");

    public EDB() throws SQLException {
        logger.setLevel(Level.INFO);
        this.url = "jdbc:mysql://"
                + SIProp.prop().getString("evoluti.host.name", "localhost") + ":" + SIProp.prop().getString("evoluti.host.port", "3306")
                + "/" + SIProp.prop().getString("evoluti.host.database", "sistema")
                + "?useLegacyDatetimeCode=false&serverTimezone=America/Bahia&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true&useSSL=false"
                + SIProp.prop().getString("evoluti.host.params", "");
        this.userName = SIProp.prop().getString("evoluti.host.user", "root");
        this.password = SIProp.prop().getString("evoluti.host.password", "mysql147");
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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            
//            try (Connection conn = DriverManager.getConnection(this.url, userName, password);
//                    PreparedStatement s = conn.prepareStatement("CREATE SCHEMA IF NOT EXISTS " + EProp.prop().getString("host.database", "sistema") + ";")) {
//                s.execute();
//            }
            connection = DriverManager.getConnection(this.url, this.userName, this.password);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Library para conexão com o banco de dados não encontrada, motivo:\n" + ex.getMessage(), "Erro ao iniciar conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        connection.close();
    }

}
