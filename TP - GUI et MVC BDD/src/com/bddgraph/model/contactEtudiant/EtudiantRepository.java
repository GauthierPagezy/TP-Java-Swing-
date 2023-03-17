package com.bddgraph.model.contactEtudiant;

import java.sql.*;
import java.util.Date;
import java.util.Stack;

public class EtudiantRepository {
    private String nom;
    private String prenom;
    private Date  dateNaissance;
    private String lieuNaissance;
    private String Nationalite;
    private String rue;
    private String ville;
    private String mail;
    private String telephone;

    private String loisirs = "";

    private String sexe;
    private String niveau;
    private String bac;
    private String filiere;
    private String cp;

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public void setNationalite(String nationalite) {
        Nationalite = nationalite;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setLoisirs(String loisirs) {
        this.loisirs += " " + loisirs;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public void setBac(String bac) {
        this.bac = bac;
    }

    public void setCp(String cp) {
        this.cp = String.valueOf(cp);
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public String getNationalite() {
        return Nationalite;
    }

    public String getRue() {
        return rue;
    }

    public String getVille() {
        return ville;
    }

    public String getMail() {
        return mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLoisirs() {
        return loisirs;
    }

    public String getSexe() {
        return sexe;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getBac() {
        return bac;
    }

    public String getFiliere() {
        return filiere;
    }

    public String getCp() {
        return cp;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", lieuNaissance='" + lieuNaissance + '\'' +
                ", Nationalite='" + Nationalite + '\'' +
                ", rue='" + rue + '\'' +
                ", ville='" + ville + '\'' +
                ", mail='" + mail + '\'' +
                ", telephone='" + telephone + '\'' +
                ", loisirs='" + loisirs + '\'' +
                ", cp=" + cp +
                ", sexe='" + sexe + '\'' +
                ", niveau='" + niveau + '\'' +
                ", filiere=" + filiere +
                ", bac=" + bac +
                '}';
    }

    public int InsertEtudiant(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        int resultSet;
        String nom = this.nom;
        String prenom = this.prenom;
        String dateNaissance = this.dateNaissance.toString();
        String lieuNaissance = this.lieuNaissance;
        String nationalite = this.Nationalite;
        String rue = this.rue;
        String cp = this.cp;
        String ville = this.ville;
        String telephone = this.telephone;
        String mail = this.mail;
        String sexe = this.sexe;
        String loisir = this.loisirs;
        String bac = this.bac;
        String filere = this.filiere;
        String niveau = this.niveau;
        try {
            String sql = "INSERT INTO etudiant (idBac, idFil,  nom, prenom, dateNaiss, LieuNaiss, sexe, nationalite, rue, cp,  ville, telephone, mail, niveau,  loisir) VALUES ((SELECT idBac FROM bac WHERE libelle = ?), (SELECT idFil FROM filiere WHERE nom = ?), ?, ?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bac);
            preparedStatement.setString(2,filere);
            preparedStatement.setString(3, nom);
            preparedStatement.setString(4, prenom);
            preparedStatement.setString(5, dateNaissance);
            preparedStatement.setString(6, lieuNaissance);
            preparedStatement.setString(7, sexe);
            preparedStatement.setString(8, nationalite);
            preparedStatement.setString(9, rue);
            preparedStatement.setInt(10, Integer.parseInt(cp));
            preparedStatement.setString(11, ville);
            preparedStatement.setString(12, telephone);
            preparedStatement.setString(13, mail);
            preparedStatement.setString(14, niveau);

            if (loisirs.isEmpty() && loisirs != null) {
                preparedStatement.setNull(15, Types.VARCHAR); // définir la valeur du paramètre à null
            } else {
                preparedStatement.setString(15, loisirs); // définir la valeur du paramètre à la chaîne de caractères
            }
            resultSet = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // fermer les ressources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultSet;
    }


}