import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// GUI class to display part 1 of the GUI
public class TablePanel extends JFrame
{
    //
    public TablePanel(List<InflationCollection> data)
    {
        // set frame title
        super("Inflation Data");

        // Create panels
        statsPanel statsPanel = new statsPanel(data);
        ChartsPanel chartsPanel = new ChartsPanel(data);
        DetailsPanel detailsPanel = new DetailsPanel(data, statsPanel, chartsPanel);


        // colum headers to appear at top of frame
        String[] columnNames = {"Country", "Year", "Inflation Rate"};

        // create a table with the column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // use a for loop to go through data list and populate the table
        for(InflationCollection inflation : data)
        {
            Object[] row = { inflation.getCountry(), inflation.getYear(), inflation.getInflationRate() };
            model.addRow(row); // add data to the model
        }
        // create JTable that uses model and contains our rows of data
        JTable table = new JTable(model);
        // use scroll pane because lots of rows
        JScrollPane scrollPane = new JScrollPane(table);

        // add a tabbed pane to hold all panels
        JTabbedPane tabbedPane = new JTabbedPane();


        // add panels
        tabbedPane.addTab("DetailData", detailsPanel);
        tabbedPane.addTab("Stats", statsPanel);
        tabbedPane.addTab("Charts", chartsPanel);

        // use a JSplitPane to simultaneously use
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, tabbedPane);
        splitPane.setDividerLocation(200);

        // add the split pane to frame
        add(splitPane, BorderLayout.CENTER);

        // setup frame
        add(scrollPane, BorderLayout.EAST);
        add(tabbedPane, BorderLayout.CENTER);
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    // method to launch GUI from importData class
    public static void display(List<InflationCollection> data)
    {
        SwingUtilities.invokeLater(()->
        {
            TablePanel d = new TablePanel(data);
            d.setVisible(true);
        });
    }
}

