import org.joda.time.DateTime;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("MMM d, yyyy");
        Scanner scn = new Scanner(System.in);
        while (true) {
            System.out.print("\n\nEbay query: ");
            String query = scn.nextLine();
            List<Listing> listings = EbayScraper.getListing(query);
            for (Listing l : listings) {
                System.out.println(l);
            }
            ListingData data = new ListingData(listings);
        }

    }
}
