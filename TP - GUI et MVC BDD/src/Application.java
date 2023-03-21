import com.bddgraph.controller.LoginController;
import com.bddgraph.model.Connexion;
import com.bddgraph.model.contactEtudiant.EtudiantRepository;
import com.bddgraph.view.ResEtudiantView;

import javax.swing.*;
import java.sql.Connection;
import java.util.Date;

public class Application {
    public static void main(String[] args) {
        Connection connection = Connexion.getConnection();
        LoginController controler = new LoginController();
        controler.lancerApplication();

    }
}

