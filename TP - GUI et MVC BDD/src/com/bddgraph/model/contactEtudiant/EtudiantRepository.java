package com.bddgraph.model.contactEtudiant;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EtudiantRepository {
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String lieuNaissance;
    private String nationalite;
    private String rue;
    private String ville;
    private String mail;
    private String telephone;
    private String loisirs;
    private String sexe;
    private String niveau;

    // Ici bac et filiere devraient être des int (idFil, idBac) faisant référence aux Modeles Filieres et Bacs
    private String bac;
    private String filiere;
    private int cp;

    public EtudiantRepository(String nom, String prenom, Date dateNaissance, String lieuNaissance, String nationalite, String rue, String ville, String mail, String telephone,String sexe, String niveau, String bac, String filiere, int cp) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.nationalite = nationalite;
        this.rue = rue;
        this.ville = ville;
        this.mail = mail;
        this.telephone = telephone;
        this.sexe = sexe;
        this.niveau = niveau;
        this.bac = bac;
        this.loisirs =  "";
        this.filiere = filiere;
        this.cp = cp;
    }

    public EtudiantRepository(){}

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
        this.nationalite = nationalite;
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

    public void setCp(int cp) {
        this.cp = cp;
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
        return nationalite;
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

    public int getCp() {
        return cp;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", lieuNaissance='" + lieuNaissance + '\'' +
                ", Nationalite='" + nationalite + '\'' +
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

    public int insertEtudiant(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        int resultSet;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "INSERT INTO etudiant (idBac, idFil,  nom, prenom, dateNaiss, LieuNaiss, sexe, nationalite, rue, cp,  ville, telephone, mail, niveau,loisir) VALUES ((SELECT idBac FROM bac WHERE libelle = ?), (SELECT idFil FROM filiere WHERE nom = ?), ?, ?,?,?,?,?,?,?,?,?,?,?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bac);
            preparedStatement.setString(2, filiere);
            preparedStatement.setString(3, nom);
            preparedStatement.setString(4, prenom);
            preparedStatement.setString(5, simpleDateFormat.format(dateNaissance));
            preparedStatement.setString(6, lieuNaissance);
            preparedStatement.setString(7, sexe);
            preparedStatement.setString(8, nationalite);
            preparedStatement.setString(9, rue);
            preparedStatement.setInt(10, cp);
            preparedStatement.setString(11, ville);
            preparedStatement.setString(12, telephone);
            preparedStatement.setString(13, mail);
            preparedStatement.setString(14, niveau);
            preparedStatement.setString(15,loisirs);
            resultSet = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public ResultSet checkEtudiantInDataBase(Connection connection) {
        java.sql.PreparedStatement pstmt;
        ResultSet resultSet = null;
        try {
            String request = "SELECT count(*) FROM etudiant WHERE (nom = ? AND prenom = ? AND dateNaiss = ? AND lieuNaiss = ?) OR telephone = ?";
            pstmt = connection.prepareStatement(request);
            pstmt.setString(1, this.nom);
            pstmt.setString(2, this.prenom);
            pstmt.setString(3, this.dateNaissance.toString());
            pstmt.setString(4,this.lieuNaissance);
            pstmt.setString(5, this.telephone);
            resultSet = pstmt.executeQuery();
            System.out.println(resultSet);
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement " + e.getMessage());
        }
        return resultSet;
    }

    public int countEtudiantInDataBase(Connection connection) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        int nbEtud = 0;
        try {
            String request = "Select count(*) as nbEtud FROM etudiant";
            stmt = (Statement) connection.createStatement();
            resultSet = stmt.executeQuery(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            while(resultSet.next()){
                nbEtud = resultSet.getInt("nbEtud");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nbEtud;
    }

    public ResultSet selectAllEtudiants(Connection connection){
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            String request = "Select idEtud, nom, prenom FROM etudiant";
            stmt = (Statement) connection.createStatement();
            return stmt.executeQuery(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


