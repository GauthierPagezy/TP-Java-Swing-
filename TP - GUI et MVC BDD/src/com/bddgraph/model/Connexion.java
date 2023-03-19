package com.bddgraph.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connexion {
    private static String url = "jdbc:mysql://localhost:3306/bddgraph";

    private static String bddLogin = "root";

    private static String bddPassword = "";
    public static Connection getConnection() {
        Properties props = new Properties();
        props.setProperty("user", bddLogin);
        props.setProperty("password", bddPassword);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement " + e.getMessage());
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement " + e.getMessage());
        }
        return con;
    }
}



