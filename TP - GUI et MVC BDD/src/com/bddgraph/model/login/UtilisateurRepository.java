package com.bddgraph.model.login;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurRepository {
    private String login;
    private String password;

    public UtilisateurRepository(String login, String password){
        this.login = login;
        this.password = password;
    }

    public ResultSet checkUserInDataBase(Connection connection){
        java.sql.PreparedStatement pstmt;
        ResultSet resultSet = null;
        try {
            String request = "SELECT count(*) FROM utilisateur WHERE login = ? AND password = ?";
            pstmt = connection.prepareStatement(request);
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            resultSet = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement " + e.getMessage());
        }
        return resultSet;
    }



}
