import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

public class importData
{
    public static List<InflationCollection> readCSV(String filepath) {
        try {
            return Files.lines(Paths.get(filepath))
                    .skip(1)                            // skip header
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length < 5) return null;
                        String country       = parts[2].trim();
                        int    year          = Integer.parseInt(parts[3].trim());
                        double inflationRate = Double.parseDouble(parts[4].trim());
                        return new InflationCollection(country, year, inflationRate);
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();  // or throw a RuntimeException
        }
    }

}
