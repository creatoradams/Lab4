import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JButton;

public class DetailsPanel extends JPanel
{
    private JLabel resultLabel; // used to display results
    private List<InflationCollection> data;

    // references to other panels to update them
    private statsPanel statsPanel;
    private ChartsPanel chartsPanel;

    public DetailsPanel(List<InflationCollection> data, statsPanel statsPanel, ChartsPanel chartsPanel)
    {
        this.data = data;
        this.statsPanel = statsPanel;
        this.chartsPanel = chartsPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 300));

        // extract different countries from the data
        Set<String> diffCountry = new HashSet<>();
        for(InflationCollection i : data)
        {
            diffCountry.add(i.getCountry());
        }
        // convert the set of countries to an array
        String[] countries = diffCountry.toArray(new String[0]);

        // sort the array alphabetically, easier on the eye imo
        Arrays.sort(countries); // sort the countries alphabetically

        // create a dropdown for selecting a country
        JComboBox<String> country = new JComboBox<>(countries); // create combo box for countries

        // extract unique years from the data
        Set<Integer> yearSet = new HashSet<>();

        // loop through the data and add years to the set
        for(InflationCollection i : data)
        {
            yearSet.add(i.getYear());
        }
        // convert from set to a list
        List<Integer> allYears;
        allYears = new ArrayList<>(yearSet);

        // sort ascending
        Collections.sort(allYears);
        // convert to array
        Integer[] years = allYears.toArray(new Integer[0]);

        // Create a JList
        JList<Integer> yearList = new JList<>(years);
        yearList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        yearList.setVisibleRowCount(5); // Just so you see a few items at once in the GUI

        // create a button to show inflation
        JButton button = new JButton("Show Inflation");
        button.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                // retrieve the selected country
                String selectedCountry = (String) country.getSelectedItem();
                // retrieve the selected year
                List<Integer> selectedYears = yearList.getSelectedValuesList();

                // Filter the data based on the selections
                List<InflationCollection> filteredData = data.stream()
                        .filter(i -> i.getCountry().equals(selectedCountry))
                        .filter(i -> selectedYears.contains(i.getYear()))
                        .collect(Collectors.toList());

                // update statsPanel with filtered data
                statsPanel.updateStats(filteredData);

                // update the chartsPanel with new filters
                chartsPanel.setFilters(selectedCountry, null);

            }
        });


        // add panel components
        add(new JLabel("Select Country"));
        add(country);
        add(new JLabel("Select Year (hold shift or ctrl to select multiple)"));
        add(yearList);
        add(button);
        resultLabel = new JLabel("Select a country and year to view inflation rate");
        add(resultLabel);


    }
    // method to look up inflation data for a given country and year
    private double getInflation(String country, int year)
    {
        for (InflationCollection i : data)
        {
            if (i.getCountry().equals(country) && i.getYear() == year)
            {
                return i.getInflationRate();
            }
        }
        return 0.0; // return 0 if no matching record is found
    }
}
