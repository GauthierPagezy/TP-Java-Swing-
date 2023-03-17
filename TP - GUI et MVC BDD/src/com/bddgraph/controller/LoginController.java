package com.bddgraph.controller;

import com.bddgraph.model.Connexion;
import com.bddgraph.model.contactEtudiant.BacsRepository;
import com.bddgraph.model.contactEtudiant.FilieresRepository;
import com.bddgraph.model.contactEtudiant.EtudiantRepository;
import com.bddgraph.model.login.LoginRepository;
import com.bddgraph.view.LoginView;
import com.bddgraph.view.ContactEtudiantView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;


public class LoginController {

    private final LoginView loginView = new LoginView();
    private final ContactEtudiantView contactEtudiantView = new ContactEtudiantView();
    private final Connection connection = Connexion.getConnection();


    public LoginController() {
        // Méthodes contenants les Listeners des widgets de LoginView
        setComfirmerBtnComportment();
        setComfirmerBtnAcces();
    }

    public void lancerApplication() {
        loginView.afficherVue();
        System.out.println(connection);
    }


    public void setComfirmerBtnComportment(){
        // Comportemement du Bouton Comfirmer de la page login
        loginView.getConfirmerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(connection ==  null){
                    JOptionPane.showMessageDialog(null, "Vérifiez la connexion internet");
                }
                // Récupération des valeurs de la vue
                String login = String.valueOf(loginView.getLoginTextField().getText());
                String password = String.valueOf(loginView.getPasswordField().getPassword());

                // Transmission au modèle
                LoginRepository loginRepository = new LoginRepository(login, password);
                ResultSet resultSet = loginRepository.checkUserInDataBase(connection);
                try {
                    if (resultSet.next() && resultSet.getInt(1) == 1) {
                        loginView.dispose();
                        ContactEtudiantController contactEtudiantController = new ContactEtudiantController();

                    } else {
                        loginView.setPasswordField("");
                        JOptionPane.showMessageDialog(null, "Login ou mot de passe incorrect");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setComfirmerBtnAcces(){
        // Blocage du bouton Comfirmer si les champs ne sont pas remplis
        loginView.getLoginTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkComfirmerBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkComfirmerBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkComfirmerBtn();
            }
        });
        loginView.getPasswordField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkComfirmerBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkComfirmerBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkComfirmerBtn();
            }
        });
        loginView.getConfirmerButton().setEnabled(false);
    }

    public void checkComfirmerBtn() {
        boolean value = !loginView.getLoginTextField().getText().isBlank() && (!(loginView.getPasswordField().getPassword().length == 0));
        loginView.getConfirmerButton().setEnabled(value);
    }




}



