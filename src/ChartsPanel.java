import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ChartsPanel extends JPanel
{
    private final List<InflationCollection> data;
    private String selectedCountry = null; // which country to display
    private Integer selectedYear = null; // which year to display

    public ChartsPanel(List<InflationCollection> data)
    {
        this.data = data;
        setPreferredSize(new Dimension(500, 500));
    }

    // method to draw chart based on what year and country is picked
    public void setFilters(String country, Integer year)
    {
        this.selectedCountry = country;
        this.selectedYear = year;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g); // Clears the background

        if (data == null || data.isEmpty())
        {
            // If there's no data, just display a message
            g.drawString("No data to display", 10, 20);
            return;
        }

        // Convert the Graphics object to Graphics2D for better control
        Graphics2D g2 = (Graphics2D) g;

        //intelliJ recommended this
        List<InflationCollection> filteredData = new ArrayList<>();
        for (InflationCollection datum : data) {
            if (selectedCountry == null || datum.getCountry().equals(selectedCountry)) {
                if ((selectedYear == null) || (datum.getYear() == selectedYear)) {
                    filteredData.add(datum);
                }
            }
        }

        // sort the filtered data by year
        filteredData.sort(Comparator.comparingDouble(InflationCollection::getYear));

        // if no selections, display a message
        if (filteredData.isEmpty())
        {
            g.drawString("No data to display", 10, 20);
            return;
        }

        // Determine the min and max for both X (year) and Y (inflation)
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (InflationCollection i : filteredData)
        {
            double x = i.getYear();
            double y = i.getInflationRate();
            if (x < minX)
                minX = x;
            if (x > maxX)
                maxX = x;
            if (y < minY)
                minY = y;
            if (y > maxY)
                maxY = y;
        }

        // Define some margins for drawing
        int padding = 40;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int chartWidth = panelWidth - 2 * padding;
        int chartHeight = panelHeight - 2 * padding;


        // X-axis line
        g2.drawLine(padding, panelHeight - padding, panelWidth - padding, panelHeight - padding);
        // Y-axis line
        g2.drawLine(padding, padding, padding, panelHeight - padding);

        // store previous point coordinates
        int previousX = -1;
        int previousY = -1;

        // Plot each data point
        for (InflationCollection i : data)
        {
            double xValue = i.getYear();
            double yValue = i.getInflationRate();

            // Convert data value to panel coordinates
            int xPixel = (int) (padding + (xValue - minX) / (maxX - minX) * chartWidth);
            int yPixel = (int) ((panelHeight - padding) - (yValue - minY) / (maxY - minY) * chartHeight);

            // Draw a small oval or rectangle to represent the point
            int pointSize = 6;
            g2.fillOval(xPixel - pointSize/2, yPixel - pointSize/2, pointSize, pointSize);

            // draw a line connecting previous point and next point
            if(previousX != -1 && previousY != -1)
            {
                g2.drawLine(previousX, previousY, xPixel, yPixel);
            }
            previousX = xPixel;
            previousY = yPixel;
        }

        // Add axis labels, ticks, or a chart title
        g2.drawString("20 Year Inflation", panelWidth / 2 - 50, padding / 2);
        // X-axis label
        g2.drawString("Year", panelWidth / 2, panelHeight - 10);
        // Y-axis label (rotated)
        g2.rotate(-Math.PI / 2);
        g2.drawString("Inflation Rate", -panelHeight / 2, 20);
        g2.rotate(Math.PI / 2); // rotate back
    }
}
