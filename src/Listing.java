import org.joda.time.DateTime;
import org.joda.time.Days;

public class Listing {
    public String name;
    public double price;
    public DateTime dateSold;

    public Listing(String name, double price, String dateSold) {
        this.name = name;
        this.price = price;
        this.dateSold = EbayScraper.convertDate(dateSold);
    }
    public int daysSinceSold(){
        DateTime now = DateTime.now();
        return Days.daysBetween(dateSold.toLocalDate(),now.toLocalDate()).getDays();

    }
    @Override
    public String toString() {
        return "\nListing Name: "+name+"\nPrice sold: "+price+"\nDays since sold: "+daysSinceSold();
    }
}
