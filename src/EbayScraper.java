import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.joda.time.DateTime;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EbayScraper {
    public static DateTime convertDate(String str) {
        DateFormat df = new SimpleDateFormat("MMM d, yyyy");
        DateTime date = null;
        try {
            date = new DateTime(df.parse(str));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static List<Listing> getListing(String query) {
        String queryFormated = query.replaceAll("  ", " ").replaceAll("  ", " ").replaceAll(" ", "+");
        String url = "https:" + "//www.ebay.com/sch/i.html?_from=R40&_nkw=" + queryFormated + "&rt=nc&LH_Sold=1&LH_Complete=1";
        CleanerProperties props = new CleanerProperties();
        props.setTranslateSpecialEntities(true);
        props.setTransResCharsToNCR(true);
        props.setOmitComments(true);

        //pull data for either today or tommorow
        try {
            TagNode tagNode = new HtmlCleaner(props).clean(
                    new URL(url));
            TagNode baseObj =(TagNode) tagNode.evaluateXPath("//*[@id=\"srp-river-results\"]/ul")[0];
            Object[] listings = baseObj.evaluateXPath("/li");
            List<Listing> listingsObj=new ArrayList<>();
            for(Object node:listings){
                Double price;
                String listingName;
                String soldString;
                TagNode n=(TagNode) node;
                listingName=((TagNode)n.evaluateXPath("/div/div[2]/a/h3")[0]).getText().toString();
                soldString=((TagNode)n.evaluateXPath("/div/div[2]/div[1]/div/span[1]")[0]).getText().toString().replaceAll("Sold  ","");
                String priceString=n.findElementByAttValue("class","s-item__price",true,true).getText().toString().replaceAll(java.util.regex.Pattern.quote("$"),"").replaceAll(",","");
                if(!priceString.contains("to")) {
                    price = Double.parseDouble(priceString);
                    listingsObj.add(new Listing(listingName, price, soldString));
                }
            }
            return listingsObj;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (XPatherException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
