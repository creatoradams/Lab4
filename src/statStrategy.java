import java.util.List;

public interface statStrategy
{
    // the name to display
    String getName();

    // run on the given filtered data
    double compute(List<InflationCollection> collections);
}
