package com.bddgraph.controller;
import com.bddgraph.model.Connexion;
import com.bddgraph.model.login.UtilisateurRepository;
import com.bddgraph.view.LoginView;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
public class LoginController {

    private final LoginView loginView = new LoginView();

    private final Connection connection = Connexion.getConnection();


    public LoginController() {
        // Méthodes contenants les Listeners des widgets de LoginView
        setComfirmerBtnComportement();
        setComfirmerBtnAcces();
        setAnnulerBtnBtnComportement();
    }

    public void lancerApplication() {
        loginView.afficherVue();
        System.out.println(connection);
    }
    public void setComfirmerBtnComportement() {
        // Comportemement du Bouton Comfirmer de la page login
        loginView.getConfirmerButton().addActionListener(e -> {
            if (connection == null) {
                JOptionPane.showMessageDialog(null, "Vérifiez la connexion internet");
            }
            // Récupération des valeurs de la vue
            String login = String.valueOf(loginView.getLoginTextField().getText());
            String password = String.valueOf(loginView.getPasswordField().getPassword());

            // Transmission au modèle
            UtilisateurRepository loginRepository = new UtilisateurRepository(login, password);
            ResultSet resultSet = loginRepository.checkUserInDataBase(connection);
            try {
                if (resultSet.next() && resultSet.getInt(1) == 1) {
                    loginView.dispose();
                    ContactEtudiantController contactEtudiantController = new ContactEtudiantController();

                } else {
                    loginView.setPasswordField("");
                    JOptionPane.showMessageDialog(null, "Login ou mot de passe incorrect");
                }
            } catch (SQLException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setComfirmerBtnAcces() {
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

    public void setAnnulerBtnBtnComportement() {
        loginView.getAnnulerButton().addActionListener(e -> {
            if (loginView.getLoginTextField().getText().isEmpty() && loginView.getPasswordField().getPassword().length == 0) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir quitter ?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
            loginView.getLoginTextField().setText("");
            loginView.getPasswordField().setText("");
        });
    }

    public void checkComfirmerBtn() {
        boolean value = !loginView.getLoginTextField().getText().isBlank() && (!(loginView.getPasswordField().getPassword().length == 0));
        loginView.getConfirmerButton().setEnabled(value);
    }


}



