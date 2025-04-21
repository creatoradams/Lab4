import javax.swing.*;
import java.util.List;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

public final class appController
{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
        {
            // load data from file
            List<InflationCollection> data = importData.readCSV("inflation.csv");
            // create match publisher
            SelectionPub pub = new SelectionPub();

            // instantiate the panels (panels = observers)
            statsPanel stats = new statsPanel(data);
            ChartsPanel chart = new ChartsPanel(data);
            DetailsPanel details = new DetailsPanel(data, pub);
            //TablePanel.display(data);
            // Register observers to receive matches
            pub.addListener((SelectionListener) stats);
            pub.addListener((SelectionListener) chart);

            // build and display the main window
            JFrame frame = new JFrame("Inflation Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(details, BorderLayout.WEST);
            frame.add(stats, BorderLayout.EAST);
            frame.add(chart, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}