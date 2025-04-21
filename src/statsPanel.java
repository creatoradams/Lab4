import javax.swing.*;
import java.awt.*;
import java.util.List;

public class statsPanel extends JPanel implements SelectionListener
{

    // Keep references to labels so we can update them later
    private JLabel minLabel;
    private JLabel maxLabel;
    private JLabel averageLabel;
    private JLabel totalLabel;

    public statsPanel(List<InflationCollection> data)
    {
        setLayout(new GridLayout(4, 1));

        // Initialize labels
        minLabel = new JLabel();
        maxLabel = new JLabel();
        averageLabel = new JLabel();
        totalLabel = new JLabel();

        // Add them to the panel
        add(minLabel);
        add(maxLabel);
        add(averageLabel);
        add(totalLabel);

        // Calculate initial stats
        updateStats(data);
    }

    public void updateStats(List<InflationCollection> newData)
    {
        if (newData == null || newData.isEmpty())
        {
            minLabel.setText("Minimum Inflation Rate: N/A");
            maxLabel.setText("Maximum Inflation Rate: N/A");
            averageLabel.setText("Average Inflation Rate: N/A");
            totalLabel.setText("Total Inflation Rate: N/A");
            return;
        }

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double total = 0.0;

        for (InflationCollection i : newData)
        {
            double rate = i.getInflationRate();
            if (rate < min) min = rate;
            if (rate > max) max = rate;
            total += rate;
        }
        double average = total / newData.size();

        // Round to 2 decimals using String.format
        minLabel.setText("Minimum Inflation Rate: " + String.format("%.2f", min));
        maxLabel.setText("Maximum Inflation Rate: " + String.format("%.2f", max));
        averageLabel.setText("Average Inflation Rate: " + String.format("%.2f", average));
        totalLabel.setText("Total Inflation Rate: " + String.format("%.2f", total));
    }

    @Override
    public void selectionChanged(Selection event)
    {
        updateStats(event.slice());
    }
}
