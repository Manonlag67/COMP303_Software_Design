import java.time.LocalDate;

/**
 * The Event Manager from EventBrite, Bob, is the client who will be using an object of the 'EventManagement' class
 * to schedule / manage events and calculate the expected profit from different events hosted on EventBrite.
 */
public class Driver {
    public static void main(String[] args) {

        // SETTING UP THE ENVIRONMENT :
        // *************************************************************************************************************
        EventManagement EventBrite = new EventManagement();
        EventBrite.addVIP("Michael Jackson");
        EventBrite.addVIP("George Washington");
        EventBrite.addVIP("Bruce Lee");

        /**
         *  SCHEDULE ALL TYPES OF EVENTS :
         */

        System.out.println("CREATION OF ALL TYPES OF EVENT (CONCERT / GALA / SCREENING / WORKSHOP) : ");
        // *************************************************************************************************************
        EventBrite.addConcertEvent("Concert 2022", LocalDate.of(2022,05,22),Location.ParcJeanDrapeau,25.0,100,"Bob Marley",EventBrite.getVIPs());
        EventBrite.addGalaEvent("Gala Night 2022",LocalDate.of(2022,5,15),Location.OlympicStadium,29.99,100,EventBrite.getVIPs());
        EventBrite.addScreeningEvent("Screening - Interstellar 2022",LocalDate.of(2022,06,07),Location.BellCentre,10.0,30, Screening.Rating.PG_13);
        EventBrite.addWorkshopEvent("Workshop Painting 2022",LocalDate.of(2022,05,17),Location.ParcJeanDrapeau,5.5,15);
        EventBrite.printHostedEvents();

        System.out.println("CREATION OF A FESTIVAL EVENT : ");
        // *************************************************************************************************************
        EventBrite.addFestival("Summer Festival 2022", EventBrite.getHostedEvents());
        EventBrite.printHostedEvents();

        System.out.println("CREATION OF 'ComingSoon' EVENT : ");
        // *************************************************************************************************************
        EventBrite.addConcertEvent("Concert 2023",LocalDate.of(2023,05,22),null,null,null,"Joey Badass", EventBrite.getVIPs());
        EventBrite.addGalaEvent("Gala Night 2023",LocalDate.of(2023,5,15),null,null,null,EventBrite.getVIPs());
        EventBrite.addScreeningEvent("Screening - Interstellar 2023",LocalDate.of(2023,06,07),null,null,null, Screening.Rating.PG_13);
        EventBrite.addWorkshopEvent("Workshop Painting 2023",LocalDate.of(2023,05,17),null,null,null);
        EventBrite.printHostedEvents();

        System.out.println("CREATION OF DUPLICATE EVENT : ");
        // *************************************************************************************************************
        EventBrite.addConcertEvent("Concert2.0 2022", LocalDate.of(2022,05,22),Location.BellCentre,25.0,100,"Bob Marley",EventBrite.getVIPs());
        EventBrite.addGalaEvent("Gala Night2.0 2022",LocalDate.of(2022,5,15),Location.OlympicStadium,29.99,100,EventBrite.getVIPs());
        EventBrite.addScreeningEvent("Screening2.0 - Interstellar 2022",LocalDate.of(2022,06,07),Location.BellCentre,10.0,30, Screening.Rating.PG_13);
        EventBrite.addWorkshopEvent("Workshop2.0 Painting 2022",LocalDate.of(2022,05,17),Location.ParcJeanDrapeau,5.5,15);

        // *************************************************************************************************************

        /**
         *  FILTERING ALL HOSTED EVENTS :
         */

        System.out.println("\nFILTERING HOSTED EVENTS BY PRICE RANGE B/W $10.0 - $30.0: ");
        // *************************************************************************************************************
        EventBrite.resetFilters();
        EventBrite.setFilterByPriceRange(10.0,30.0);

        FilteredResult result1 = EventBrite.filterHostedEvents();
        result1.printFilteredResult();

        System.out.println("FILTERING HOSTED EVENTS BY LOCATION ParcJeanDrapeau: ");
        // *************************************************************************************************************
        EventBrite.resetFilters();
        EventBrite.setFilterByLocation(Location.ParcJeanDrapeau);

        FilteredResult result2 = EventBrite.filterHostedEvents();
        result2.printFilteredResult();

        System.out.println("FILTERING HOSTED EVENTS BY PRICE RANGE + BY LOCATION: ");
        // *************************************************************************************************************
        EventBrite.resetFilters();
        EventBrite.setFilterByPriceRange(10.0,30.0);
        EventBrite.setFilterByLocation(Location.ParcJeanDrapeau);

        FilteredResult result3 = EventBrite.filterHostedEvents();
        result3.printFilteredResult();

        /**
         *  CALCULATE EXPECTED PROFIT OF DIFFERENT FILTERED_RESULTS :
         */
        // Using Default Profit Percentage (= 20% for all types of event) :
        System.out.println("Using Default Profit Percentage (= 20% for all types of event) : ");
        System.out.println("Expected Profit for result1 : "+result1.getExpectedProfit());
        System.out.println("Expected Profit for result2 : "+result2.getExpectedProfit());
        System.out.println("Expected Profit for result3 : "+result3.getExpectedProfit());
        System.out.println();

        // Using Profit Percentage selected by Bob :
        System.out.println("Using Profit Percentage selected by Bob : ");
        EventBrite.setProfitConcert(50);
        EventBrite.setProfitGala(40);
        EventBrite.setProfitScreening(30);
        EventBrite.setProfitWorkshop(20);
        System.out.println("Expected Profit for result1 : "+result1.getExpectedProfit());
        System.out.println("Expected Profit for result2 : "+result2.getExpectedProfit());
        System.out.println("Expected Profit for result3 : "+result3.getExpectedProfit());
        System.out.println();

        EventBrite.addConcertEvent(null, LocalDate.of(2022,05,22),Location.ParcJeanDrapeau,25.0,100,"Bob Marley",EventBrite.getVIPs());

    }
}
