import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

// GUI class to display part 1 of the GUI
public class TablePanel extends JFrame implements SelectionListener
{
    private final DefaultTableModel model;
    public TablePanel(List<InflationCollection> data) {
        // set frame title
        super("Inflation Dashboard");

        // Create the publisher
        SelectionPub publisher = new SelectionPub();

        //create the observers
        statsPanel stats = new statsPanel(data);
        ChartsPanel charts = new ChartsPanel(data);
        DetailsPanel details = new DetailsPanel(data, publisher);

        // setup observers
        publisher.addListener(this);
        publisher.addListener(stats);
        publisher.addListener(charts);

        // build the data table
        String[] cols = {"Country", "Year", "Inflation Rate"};
        model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // fill in all data initially
        data.forEach(d -> model.addRow(new Object[]{d.getCountry(),
                d.getYear(), d.getInflationRate()}));

        // put into tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Table", scrollPane);
        tabs.addTab("Filter", details);
        tabs.addTab("Stats", stats);
        tabs.addTab("Charts", charts);

        // final
        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    @Override
    public void selectionChanged(Selection event)
    {
        // wipe old rows
        model.setRowCount(0);

        // repopulate
        for(InflationCollection i : event.slice())
        {
            model.addRow(new Object[]{
                    i.getCountry(),
                    i.getYear(),
                    i.getInflationRate()
            });
        }
    }

    public static void display(List<InflationCollection> data)
    {
        SwingUtilities.invokeLater(() -> {
            TablePanel panel = new TablePanel(data);
            panel.setVisible(true);
        });
    }
}

