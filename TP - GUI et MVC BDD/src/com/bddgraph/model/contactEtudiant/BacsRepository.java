package com.bddgraph.model.contactEtudiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BacsRepository {


    public ResultSet getBacs(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Select libelle FROM bac");
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
