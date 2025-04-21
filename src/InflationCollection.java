import java.util.List;

// this class holds data from each row of the file

public class InflationCollection
{
    // where to store data of each row
    private String country;
    private int year;
    private double inflationRate;

    public InflationCollection(String country, int year, double inflationRate)
    {
        this.country = country;
        this.year = year;
        this.inflationRate = inflationRate;
    }

    // setup getters
    public  String getCountry()
    {
        return country;
    }
    public int getYear()
    {
        return year;
    }

    public double getInflationRate()
    {
        return inflationRate;
    }

    @Override
    public String toString()
    {
        return "Country: " + country + ", Year: " + year + ", Inflation Rate: " + inflationRate;
    }
}
