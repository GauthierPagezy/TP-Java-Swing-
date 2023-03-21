package com.bddgraph.view;

import javax.swing.*;
import java.awt.event.ActionListener;


public class LoginView extends JFrame  {
    private JPasswordField passwordField;
    private JTextField loginTextField;
    private JButton annulerButton;
    private JPanel connexionPanel;
    private JPanel titleLabelPanel;
    private JPanel textsFieldsPanel;
    private JPanel buttonsPanel;
    private JPanel buttonLeftPanel;
    private JPanel buttonRightPanel;
    private JPanel buttonCenterPanel;
    private JButton confirmerButton;
    private JLabel JLabelTitle;
    private JLabel JLabelPhoto;
    private JLabel JLabelLogin;
    private JLabel JLabelPassord;


    public void afficherVue() {
        setTitle("Connexion");
        setSize(550, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setContentPane(connexionPanel);
        setLocationRelativeTo(null);
    }


    public JPasswordField getPasswordField(){ return  passwordField; }
    public JTextField getLoginTextField(){ return  loginTextField; }
    public void setPasswordField(String donnees) {
        passwordField.setText(donnees);
    }
    public void setLoginTextField(String donnees){loginTextField.setText(donnees);}
    public JButton getConfirmerButton() {return confirmerButton;}
    public JButton getAnnulerButton() {return annulerButton;}
}

