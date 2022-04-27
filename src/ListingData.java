import org.joda.time.Days;

import java.util.*;

public class ListingData {
    List<Listing> l;
    List<Integer> timeBetweenSales;
    List<Double> prices;
    IntSummaryStatistics getDataInt(List<Integer>list){
        int[]a=list.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        return Arrays.stream(a).summaryStatistics();
    }
    DoubleSummaryStatistics getDataDouble(List<Double>list){
        double[]a=list.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
        return Arrays.stream(a).summaryStatistics();
    }

    public ListingData(List<Listing> l) {
        this.l = l;
        l.sort(Comparator.comparing((Listing l2) -> l2.dateSold.toDate()).reversed());
        prices=new ArrayList<>();
        List<Integer> timeBetweenSales=new ArrayList<>();
        for(int i =1;i<l.size()-1;i++){
            Listing l2 = l.get(i);
            Listing l1 = l.get(i-1);
            int dif = Days.daysBetween(l2.dateSold.toLocalDate(),l1.dateSold.toLocalDate()).getDays();
            prices.add(this.l.get(i).price);
            timeBetweenSales.add(dif);
        }
        this.timeBetweenSales=timeBetweenSales;
        int[]a=this.timeBetweenSales.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        IntSummaryStatistics timeBetweenSalesStats= getDataInt(timeBetweenSales);
        DoubleSummaryStatistics priceData= getDataDouble(prices);
        System.out.println("\nTime between sales stats");
        System.out.println(timeBetweenSalesStats);
        System.out.println("\nPrice Stats");
        System.out.println(priceData);
    }
}
