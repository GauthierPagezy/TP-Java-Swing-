package com.bddgraph.controller;

import com.bddgraph.model.Connexion;
import com.bddgraph.model.contactEtudiant.BacsRepository;
import com.bddgraph.model.contactEtudiant.EtudiantRepository;
import com.bddgraph.model.contactEtudiant.FilieresRepository;
import com.bddgraph.view.ContactEtudiantView;
import com.bddgraph.view.LoginView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class ContactEtudiantController {
    private ContactEtudiantView contactEtudiantView;
    Connection connection = Connexion.getConnection();
    private boolean isFirstClickDate = true;
    private boolean isFirstClickCp = true;
    private boolean isFirstClickTel = true;

    public ContactEtudiantController() throws ParseException, SQLException {
        // Appel de la vue pour afficher la fenêtre
        contactEtudiantView = new ContactEtudiantView();
        contactEtudiantView.afficherVue();
        // Récupération des filieres et bacs du modèle
        FilieresRepository filieresRepository = new FilieresRepository();
        BacsRepository bacsRepository = new BacsRepository();
        ResultSet filieresResultSet;

        // Transmission des filieres et bacs et à la vue pour les comboBox
        filieresResultSet = filieresRepository.getFilieres(connection);
        ResultSet bacResultSet = bacsRepository.getBacs(connection);
        contactEtudiantView.remplirComboBoxBac(bacResultSet);
        contactEtudiantView.remplirComboBoxFileres(filieresResultSet);

        // Méthodes contenants les Listeners des widgets de ContactEtudiantView
        setValiderBtnComportement();
        setTexfieldDateAndCpComportement();
        setValiderBtnAcces();
        setAnnulerBtnComportement();
        setQuitterBtnComportement();
    }

    // Décrit le comportement du bouton valider de la page Contact Etudiant
    public void setValiderBtnComportement() {
        contactEtudiantView.getValiderButton().addActionListener(e -> {
            System.out.println("Appui");
            try {
                if (isFormularRecordable(contactEtudiantView.getFormularDatas())) {
                    EtudiantRepository etudiant = createEtudiant();
                    System.out.println(etudiant.toString() + "\n\n");
                    if (!existsInDataBase(etudiant)) {
                        // On enregistre l'étudiant et l'affiche
                        etudiant.insertEtudiant(connection);
                        contactEtudiantView.afficherEtudiant(etudiant);
                    }
                }
            } catch (ParseException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // La politique des champs nécessaires est définie ici. Loisir est optionel
    public void checkValiderBtn() {
        boolean value;
        value = !contactEtudiantView.getTextFieldNom().getText().isBlank() &&
                !contactEtudiantView.getTextFieldPrenom().getText().trim().isEmpty() &&
                !contactEtudiantView.getFormattedTextFieldDateNaiss().getText().isBlank() &&
                !contactEtudiantView.getTextFieldNationalite().getText().isBlank() &&
                contactEtudiantView.getBoutonsRadio().getSelection() != null &&
                !contactEtudiantView.getTextFieldRue().getText().isBlank() &&
                !contactEtudiantView.getFormattedTextFieldCp().getText().isBlank() &&
                !contactEtudiantView.getTextFieldVille().getText().isBlank() &&
                !contactEtudiantView.getTextFieldTelephone().getText().isBlank() &&
                !contactEtudiantView.getTextFieldMail().getText().isBlank();
        contactEtudiantView.getValiderButton().setEnabled(value);
    }

    // Vérifie si le formulaire est envoyable à la base
    public boolean isFormularRecordable(Map<String, String> formulaire) throws ParseException {
        if (formulaire != null) {
            return checkFormular(formulaire) && checkDateNaissance(formulaire);
        }
        return true;
    }

    // Vérifie si le formulaire ne contient pas de chiffres dans les champs.
    private boolean checkFormular(Map<String, String> formulaire) {
        if (!(formulaire.get("cp").length() == 5) || !(formulaire.get("telephone").trim().length() == 17)) {
            JOptionPane.showMessageDialog(null, "Le cp ou le téléphone est invalide");
            return false;
        } else {
            ArrayList<String> champsEnErreur = new ArrayList<>();
            // On verifie
            for (String champ : contactEtudiantView.getChamps()) {
                if (containADigit(formulaire.get(champ))) {
                    champsEnErreur.add(champ);
                }
            }
            // Appel de la vue pour afficher les erreurs
            if (champsEnErreur.size() > 0) {
                contactEtudiantView.afficherErreurChiffre(champsEnErreur);
                return false;
            } else {
                return true;
            }
        }
    }

    // Méthode privée pour vérifier la date de naissance
    private boolean checkDateNaissance(Map<String, String> formulaire) {
        try {
            if (formulaire != null) {
                String sDateNaiss = formulaire.get("dateNaiss");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date dateNaiss = formatter.parse(sDateNaiss);
                LocalDate localDateActuelle = LocalDate.now();
                LocalDate localAgeMax = localDateActuelle.minusYears(80); // 2023 - 80 = 1943 minimum
                LocalDate localAgeMin = localDateActuelle.minusYears(10); // 2023 - 10 = 2013 minimum
                Date dateMin = convertLocalDateToDate(localAgeMin);
                Date dateMax = convertLocalDateToDate(localAgeMax);
                if (dateNaiss.after(dateMin) || dateNaiss.before(dateMax)) {
                    JOptionPane.showMessageDialog(null, "L'étudiant présenté n'a pas l'âge requis");
                    return false;
                } else {
                    return true;
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Attention, le format de la date ne semble pas valide");
            return false;
        }
        return false;
    }

    // Méthode qui créé un objet Etudiant
    public EtudiantRepository createEtudiant() throws ParseException {
        Map<String, String> formularDatas = contactEtudiantView.getFormularDatas();
        EtudiantRepository etudiant = new EtudiantRepository();
        etudiant.setNom(formularDatas.get("nom"));
        etudiant.setPrenom(formularDatas.get("prenom"));
        etudiant.setLieuNaissance(formularDatas.get("lieuNaiss"));
        etudiant.setNationalite(formularDatas.get("nationalite"));
        etudiant.setRue(formularDatas.get("rue"));
        etudiant.setCp(Integer.parseInt(formularDatas.get("cp")));
        etudiant.setVille(formularDatas.get("ville"));
        etudiant.setTelephone(formularDatas.get("telephone"));
        etudiant.setMail(formularDatas.get("mail"));

        String sDateNaiss = formularDatas.get("dateNaiss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaiss = formatter.parse(sDateNaiss);
        etudiant.setDateNaissance(dateNaiss);

        if (contactEtudiantView.getHommeRadioButton().isSelected()) {
            etudiant.setSexe(contactEtudiantView.getHommeRadioButton().getText());
        } else etudiant.setSexe(contactEtudiantView.getFemmeRadioButton().getText());

        if (contactEtudiantView.getSportsCheckBox().isSelected()) {
            etudiant.setLoisirs(contactEtudiantView.getSportsCheckBox().getText());
        }
        if (contactEtudiantView.getVoyagesCheckBox().isSelected()) {
            etudiant.setLoisirs(contactEtudiantView.getVoyagesCheckBox().getText());
        }
        if (contactEtudiantView.getMusiquesCheckBox().isSelected()) {
            etudiant.setLoisirs(contactEtudiantView.getMusiquesCheckBox().getText());
        }
        if (contactEtudiantView.getLectureCheckBox().isSelected()) {
            etudiant.setLoisirs(contactEtudiantView.getLectureCheckBox().getText());
        }
        etudiant.setFiliere((String) contactEtudiantView.getComboBoxFiliere().getSelectedItem());
        etudiant.setBac((String) contactEtudiantView.getComboBoxBac().getSelectedItem());
        etudiant.setNiveau(String.valueOf(contactEtudiantView.getComboBoxNiveau().getSelectedItem()));
        return etudiant;
    }

    // Méthode privée pour convertir un objet LocalDate en Date
    private Date convertLocalDateToDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    // Méthode privée pour tester si une chaine de charactères contient un chiffre
    private boolean containADigit(String chaine) {
        for (int i = 0; i < chaine.length(); i++) {
            if (Character.isDigit(chaine.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // Méthode qui appelle le modèle et vérifie que l'utilisateur n'existe pas déja dans la base
    private boolean existsInDataBase(EtudiantRepository etudiantRepository) {
        boolean exist = false;
        ResultSet resultSet = etudiantRepository.checkEtudiantInDataBase(connection);
        try {
            if (resultSet.next()) {
                if (resultSet.getInt(1) == 1) {
                    JOptionPane.showMessageDialog(null, "L'étudiant existe déja dans la base, vérifier le nom prénom date de naisse ou numéro de téléphone");
                    exist = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vérifier la connexion Internet");
            }
        } catch (SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return exist;
    }

    // Pour vérifier si l'on peut activer le bouton après modification d'un des champs
    public void setValiderBtnAcces() {
        // Blocage du bouton Comfirmer si les champs ne sont pas remplis
        contactEtudiantView.getHommeRadioButton().addActionListener(e -> checkValiderBtn());
        contactEtudiantView.getFemmeRadioButton().addActionListener(e -> checkValiderBtn());
        contactEtudiantView.getTextFieldNom().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });

        contactEtudiantView.getTextFieldPrenom().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });
        contactEtudiantView.getFormattedTextFieldDateNaiss().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });

        contactEtudiantView.getTextFieldNationalite().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });
        contactEtudiantView.getTextFieldRue().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });
        contactEtudiantView.getFormattedTextFieldCp().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });

        contactEtudiantView.getTextFieldVille().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });

        contactEtudiantView.getTextFieldTelephone().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });

        contactEtudiantView.getTextFieldMail().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkValiderBtn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkValiderBtn();
            }
        });
        contactEtudiantView.getValiderButton().setEnabled(false);
    }

    public void setAnnulerBtnComportement(){
        contactEtudiantView.getAnnulerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactEtudiantView.reinitialiserFormulaire();
                isFirstClickCp = true;
                isFirstClickDate = true;
                isFirstClickTel  = true;
            }
        });
    }

    public void setQuitterBtnComportement(){
        contactEtudiantView.getQuitterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir quitter ?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    contactEtudiantView.dispose();
                   LoginController loginController = new LoginController();
                   loginController.lancerApplication();
                }
            }
        });
    }

    /* On veut que le curseur soit placé à gauche au premier clique
       car pb avec le MaskFormatter qui nous permet de cliquer n'importe où
     */
    public void setTexfieldDateAndCpComportement() {
        contactEtudiantView.getFormattedTextFieldDateNaiss().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (isFirstClickDate) {
                    contactEtudiantView.getFormattedTextFieldDateNaiss().setCaretPosition(0);
                    isFirstClickDate = false;
                }
            }
        });

        contactEtudiantView.getFormattedTextFieldCp().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (isFirstClickCp) {
                    contactEtudiantView.getFormattedTextFieldCp().setCaretPosition(0);
                    isFirstClickCp = false;
                }
            }
        });

        contactEtudiantView.getFormattedTextFieldTel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (isFirstClickTel) {
                    contactEtudiantView.getFormattedTextFieldTel().setCaretPosition(3);
                    isFirstClickTel = false;
                }
            }
        });
    }

}




