package com.bddgraph.view;

import com.bddgraph.model.contactEtudiant.EtudiantRepository;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class ContactEtudiantView extends JFrame {
    private JPanel JPanelContactEtudiant;
    private JPanel panelTextFields;
    private JPanel panelTextfields;
    private JPanel panelRueCpVille;
    private JPanel panelDroit;
    private JPanel panelTitle;
    private JPanel panelButtons;
    private JPanel panelFiliere;

    private JTextField textFieldMail;
    private JTextField textFieldRue;
    private JTextField textFieldVille;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldLieuNaissance;
    private JTextField textFieldNationalite;
    private JFormattedTextField formattedTextFieldTel;
    private JFormattedTextField formattedTextFieldDateNaiss;
    private JFormattedTextField formattedTextFieldCP;

    private JCheckBox sportsCheckBox;
    private JCheckBox lectureCheckBox;
    private JCheckBox voyagesCheckBox;
    private JCheckBox musiquesCheckBox;

    private JComboBox<String> comboBoxFiliere;
    private JComboBox<String>  comboBoxNiveau;
    private JComboBox<String>  comboBoxBac;
    private JButton annulerButton;
    private JButton quitterButton;
    private JButton validerButton;
    private JRadioButton hommeRadioButton;
    private JRadioButton femmeRadioButton;
    ButtonGroup boutonsRadio = new ButtonGroup();;

    private String[] champs;
    private String[] libelles;

    public void afficherVue() throws ParseException {
        setTitle("Connexion");
        setSize(1350, 780);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        setResizable(true);
        setContentPane(JPanelContactEtudiant);
        this.setLocationRelativeTo(null);

        // Ajouter les boutons radio au groupe de boutons radio
        boutonsRadio.add(hommeRadioButton);
        boutonsRadio.add(femmeRadioButton);
        boutonsRadio.clearSelection();

        // Assignations des champs pour gérer leur erreurs
        champs = new String[]{ "nom", "prenom", "lieuNaiss", "nationalite", "ville"};
        libelles = new String[]{"Nom", "Prenom", "LieuNaiss", "Nationalite", "Ville"};

        try {
            String validCharacters = "0123456789";
            MaskFormatter maskTel = new MaskFormatter("+33 # ## ## ## ##");
            MaskFormatter maskDate = new MaskFormatter("####-##-##");
            MaskFormatter maskcp = new MaskFormatter("#####");
            maskcp.setValidCharacters(validCharacters);
            maskDate.setValidCharacters(validCharacters);
            maskTel.setValidCharacters(validCharacters);
            maskcp.install(formattedTextFieldCP);
            maskTel.install(formattedTextFieldTel);
            maskDate.install(formattedTextFieldDateNaiss);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    public JTextField getTextFieldMail() {
        return textFieldMail;
    }

    public JTextField getTextFieldRue() {
        return textFieldRue;
    }

    public JTextField getTextFieldVille() {
        return textFieldVille;
    }

    public JRadioButton getHommeRadioButton() {
        return hommeRadioButton;
    }

    public JRadioButton getFemmeRadioButton() {
        return femmeRadioButton;
    }

    public JTextField getTextFieldCP() {
        return formattedTextFieldCP;
    }

    public JComboBox<String> getComboBoxFiliere() {
        return comboBoxFiliere;
    }

    public JComboBox<String> getComboBoxNiveau() {
        return comboBoxNiveau;
    }

    public JComboBox<String> getComboBoxBac() {
        return comboBoxBac;
    }

    public JTextField getTextFieldPrenom() {
        return textFieldPrenom;
    }



    public JTextField getTextFieldLieuNaissance() {
        return textFieldLieuNaissance;
    }

    public JTextField getTextFieldNationalite() {
        return textFieldNationalite;
    }

    public JFormattedTextField getTextFieldTelephone() {
        return formattedTextFieldTel;
    }
    public String[] getChamps(){return champs;}
    public String[] getLibelles(){return libelles;}
    public JTextField getTextFieldNom() {
        return textFieldNom;
    }

    public JCheckBox getSportsCheckBox() {
        return sportsCheckBox;
    }

    public JCheckBox getLectureCheckBox() {
        return lectureCheckBox;
    }

    public JCheckBox getVoyagesCheckBox() {
        return voyagesCheckBox;
    }

    public JCheckBox getMusiquesCheckBox() {
        return musiquesCheckBox;
    }
    public ButtonGroup getBoutonsRadio() { return boutonsRadio; }

    public JFormattedTextField getFormattedTextFieldDateNaiss() {
        return formattedTextFieldDateNaiss;
    }

    public JButton getValiderButton(){return validerButton;}

    public Map<String, String> getFormularDatas() {
        Map<String, String> formular = new HashMap<>();
        formular.put("nom", textFieldNom.getText().trim());
        formular.put("prenom", textFieldPrenom.getText().trim());
        formular.put("dateNaiss", formattedTextFieldDateNaiss.getText().trim());
        formular.put("lieuNaiss", textFieldLieuNaissance.getText().trim());
        formular.put("nationalite", textFieldNationalite.getText().trim());
        formular.put("rue", textFieldRue.getText().trim());
        formular.put("cp", formattedTextFieldCP.getText().trim());
        formular.put("ville", textFieldVille.getText().trim());
        formular.put("telephone", formattedTextFieldTel.getText());
        formular.put("mail", textFieldMail.getText().trim());
        return formular;
    }

    public void remplirComboBoxBac(ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            this.comboBoxBac.addItem(resultSet.getString("libelle"));
        }
    }

    public void remplirComboBoxFileres(ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            this.comboBoxFiliere.addItem(resultSet.getString("nom"));
        }
    }

    public void afficherErreurChiffre(ArrayList<String> champsEnErreur) {
        System.out.println("Print normal : ");
        System.out.println("Les champs suivants contiennent des chiffres : " + String.join(", ", champsEnErreur));
            JOptionPane.showMessageDialog(null, "Les champs :"+ String.join(", ", champsEnErreur+" contiennent des chiffres"));
        }


    public JFormattedTextField getFormattedTextFieldCp() {
        return formattedTextFieldCP;
    }

    public JFormattedTextField getFormattedTextFieldTel() {
        return formattedTextFieldTel;
    }
}
