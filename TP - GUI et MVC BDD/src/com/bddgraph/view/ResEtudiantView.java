package com.bddgraph.view;

import com.bddgraph.model.contactEtudiant.EtudiantRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResEtudiantView extends JFrame{
    private JPanel resEtudiantPanel;
    private JTable table1;

    public ResEtudiantView(){
        setTitle("Contact Etudiant");
        setSize(1350, 780);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        setResizable(true);
        setContentPane(resEtudiantPanel);
        this.setLocationRelativeTo(null);
    }

    public void afficherTable(ResultSet liste, int nbEtudiant){
        String entete[] = {"ID", "Nom", "Prénom"};
        String data[][] = new String[nbEtudiant][3];
        try {
            int i = 0;
            while (liste.next()){
                int id = liste.getInt("idEtud");
                String nom = liste.getString("nom");
                String prenom = liste.getString("prenom");
                data[i][0] = id + "";
                data[i][1] = nom;
                data[i][2] = prenom;
                i++;
            }
            DefaultTableModel model = new DefaultTableModel(data,entete);
            table1.setModel(model);
            setTitle("Contact Etudiant");
            setSize(1350, 780);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setVisible(true);
            setResizable(true);
            setContentPane(resEtudiantPanel);
            this.setLocationRelativeTo(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
