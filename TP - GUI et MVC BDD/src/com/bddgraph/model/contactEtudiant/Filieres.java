package com.bddgraph.model.contactEtudiant;

import java.sql.*;

public class Filieres {
    public ResultSet getFilieres(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT nom FROM filiere");
        ResultSet resultSet = preparedStatement.executeQuery();
        return  resultSet;
    }
}