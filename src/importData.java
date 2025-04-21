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
    public static void main(String[] args)
    {

        String file = "inflation.csv"; // variable to hold file name
        try
        {
            // read the line by line in the file as a stream
            // holds the file data
            List<InflationCollection> data = Files.lines(Paths.get(file)).skip(1).map(line ->
                    {
               // System.out.println("Line " + line); //was used for testing
                        String[] parts = line.split(",");

                        //check to make sure the line is not empty
                        if (parts.length < 5)
                        {
                            return null;
                        }

                        // create a country column
                        String country = parts[2].trim();
                        // create Year column
                        int year = Integer.parseInt(parts[3].trim());
                        // create inflation column
                        double inflationRate = Double.parseDouble(parts[4].trim());

                        // create + return collection
                        return new InflationCollection(country, year, inflationRate);
                    })

                    //filter out null results
                    .filter(Objects::nonNull)
                    // put in list
                    .toList();

            // print the 1st line of attributes
            if (!data.isEmpty())
            {
                System.out.println("First line Attributes: " + data.getFirst());
            }

            // Print the 10th line of attributes
            if(data.size() >= 10)
            {
                System.out.println("10th line Attributes: " + data.get(9)); // 9 because we skipped header above
            }

            // print the total amount of data entries
            System.out.println("Total data entries: " + data.size());
            TablePanel.display(data);
         }

        catch (IOException e)
         {
             System.out.println("Error reading from file");
             throw new RuntimeException(file);
         }


    }

    public static List<InflationCollection> readCSV(String s)
    {
        return List.of();
    }
}
