import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;

public class DetailsPanel extends JPanel {
    private List<InflationCollection> data;

    // references to other panels to update them
    private SelectionPub publish;

    public DetailsPanel(List<InflationCollection> data, SelectionPub publish) {
        this.data = data;
        this.publish = publish;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 300));

        // build country drop down
        String[] countries = data.stream().map(InflationCollection::getCountry).distinct().toArray(String[]::new);
        JComboBox<String> countryComboBox = new JComboBox<>(countries);

        // build the year list
        Integer[] years = data.stream().map(InflationCollection::getYear).distinct().sorted().toArray(Integer[]::new);
        JList<Integer> yearList = new JList<>(years);
        yearList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // show the button "Show Inflation"
        JButton go = new JButton("Show Inflation");
        go.addActionListener(e ->
        {
            String country = (String) countryComboBox.getSelectedItem();
            List<Integer> selectYears = yearList.getSelectedValuesList();
            List<InflationCollection> slice;
            slice = data.stream()
                    .filter(x -> x.getCountry().equals(country))
                    .filter(x -> selectYears.contains(x.getYear()))
                    .collect(Collectors.toList());

            SelectionPub.publish(new Selection(country, selectYears, slice));

        });

        // assemble
        add(new JLabel("Select Country"));
        add(countryComboBox);
        add(new JLabel("Select Year"));
        add(new JScrollPane(yearList));
        add(go);
    }
}
