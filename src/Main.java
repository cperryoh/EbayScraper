import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("MMM d, yyyy");
        DateTime now = DateTime.now();
        System.out.println(df.format(now.toDate()));
        List<Listing> listings=EbayScraper.getListing("grindstone masterpiece");
        for(Listing l:listings){
            System.out.println(l);
        }
        ListingData data = new ListingData(listings);
    }
}
