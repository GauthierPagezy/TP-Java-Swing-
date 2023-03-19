import com.bddgraph.controller.LoginController;
import com.bddgraph.model.contactEtudiant.EtudiantRepository;

import javax.swing.*;
import java.util.Date;

public class Application {
    public static void main(String[] args) {
        LoginController controler = new LoginController();
        controler.lancerApplication();

        /*
        JFrame frame = new JFrame("JTable Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable table = createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setVisible(true);
*/
    }

    public static JTable createTable()
    {
        String[] columnNames = {"Résumé de l'étudiant"};
        Object[][] data = {{"Kathy", "Smith"},{"John", "Doe"}};
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        return table;
    }
}

