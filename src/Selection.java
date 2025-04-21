import java.util.List;
public record Selection(String country, List<Integer> selectYears, List<InflationCollection> slice)
{
    // Records the selected country, selected years and the filtered data.
    //static String country; // which country was picked
    static List<Integer> years; // which years were picked
    static List<InflationCollection> match; // the data matching the filter
}
