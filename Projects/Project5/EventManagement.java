import java.time.LocalDate;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to MANAGE and SCHEDULE events using the different addEvent commands.
 * IT ONLY allows the scheduling of UNIQUE Events (if bob tries
 * to schedule an event with a same data and location as a previously-created-one,
 * system stop the process of adding the duplicate event and informs of him of his mistake)
 *
 * Controller also supports functions to set the profit Percentage for all the different types of
 * Events. The software updates / informs all the instances from that class type of the change.
 * Finally, the Controlled is used to determine the revenue from different events hosted onEventBrite
 * using the default-profit-percentage(s) OR/AND the inputted-profit-percentage(s) (by Bob).
 */
public class EventManagement {

    private List<Event> aHostedEvents = new ArrayList<>(); // List of all events hosted on EventBrite
    private List<String> aVIPs = new ArrayList<>(); // List of all VIPs members on EventBrite
    private List<Filter> aFilters = new ArrayList<>(); // List all the pre-selected filters for the NEXT filtering computation.

    // Different Command Objects to delegate the task of (1) : Computing expected profit (2) : Adding Event on app
    private Command_ProfitCalculator calculator = new Command_ProfitCalculator();
    private Command_AddEvent eventCommand = new Command_AddEvent();

    /* ****************************************************************************************************************
                            USE ADD FUNCTIONS TO SCHEDULE ANY TYPE OF EVENTS ON EVENTBRITE
     **************************************************************************************************************** */
    /**
      * Method to host a new concert event with the following properties :
     * @param pName
     *              Name of Concert
     * @param pDate
     *              Date of Concert
     * @param pLocation
     *              Location of Concert (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Ticket Price of Concert (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Total number of Tickets for Concert (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pArtist
     *              Artist of Concert
     * @param pVIPs
     *              list of VIPS for the Concert
     *
     * @pre pName != null && pDate != null && pArtist != null && pVIPs != null
     */
    public void addConcertEvent(String pName, LocalDate pDate, Location pLocation, Double pTicketPrice, Integer pNumTickets, String pArtist, List<String> pVIPs)
    {
        try {
            Event newEvent;
            if (pLocation == null || pTicketPrice == null || pNumTickets == null) { // To support 'ComingSoon' Events
                newEvent = eventCommand.addEvent_Concert(pName,pDate,Optional.empty(),Optional.empty(),Optional.empty(),pArtist,pVIPs);
            } else {
                newEvent = eventCommand.addEvent_Concert(pName,pDate,Optional.of(pLocation),Optional.of(pTicketPrice),Optional.of(pNumTickets),pArtist,pVIPs);
            }
            if (newEvent == null) {
                System.out.println("An Event is already planned @"+pDate+" @"+pLocation);
            } else {
                aHostedEvents.add(newEvent);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input for addConcertEvent, please try again!");
        }
    }

    /**
     * Method to host a new Gala event with the following parameters :
     * @param pName
     *              Name of Gala
     * @param pDate
     *              Date of Gala
     * @param pLocation
     *              Optional Location of Gala (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Optional Ticket Price of Gala (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Optional Total number of Tickets for Gala (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pVIPs
     *              list of VIPS for the Gala
     * @pre pName != null && pDate != null && pVIPs != null
     */
    public void addGalaEvent(String pName, LocalDate pDate, Location pLocation, Double pTicketPrice, Integer pNumTickets, List<String> pVIPs)
    {
        try {
            Event newEvent;
            if (pLocation == null || pTicketPrice == null || pNumTickets == null) { // To support 'ComingSoon' Events
                newEvent = eventCommand.addEvent_Gala(pName,pDate,Optional.empty(),Optional.empty(),Optional.empty(),pVIPs);
            } else {
                newEvent = eventCommand.addEvent_Gala(pName,pDate,Optional.of(pLocation),Optional.of(pTicketPrice),Optional.of(pNumTickets),pVIPs);

            }
            if (newEvent == null) {
                System.out.println("An Event is already planned @"+pDate+" @"+pLocation);
            } else {
                aHostedEvents.add(newEvent);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input for addConcertEvent, please try again!");
        }
    }

    /**
     * Method to host a new Screening event with the following parameters :
     * @param pName
     *              Name of Screening
     * @param pDate
     *              Date of Screening
     * @param pLocation
     *              Location of Screening (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Ticket Price of Screening (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Total number of Tickets for Screening (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pRating
     *              Rating of Screening
     * @pre pName != null && pDate != null && pRating != null
     */
    public void addScreeningEvent(String pName, LocalDate pDate, Location pLocation, Double pTicketPrice, Integer pNumTickets, Screening.Rating pRating)
    {
        try {
            Event newEvent;
            if (pLocation == null || pTicketPrice == null || pNumTickets == null) { // To support 'ComingSoon' Events
                newEvent = eventCommand.addEvent_Screening(pName,pDate,Optional.empty(),Optional.empty(),Optional.empty(),pRating);
            } else {
                newEvent = eventCommand.addEvent_Screening(pName,pDate,Optional.of(pLocation),Optional.of(pTicketPrice),Optional.of(pNumTickets),pRating);
            }
            if (newEvent == null) {
                System.out.println("An Event is already planned @"+pDate+" @"+pLocation);
            } else {
                aHostedEvents.add(newEvent);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input for addConcertEvent, please try again!");
        }
    }

    /**
     * Method to host a new Workshop event with the following parameters, and automatically
     * finds all its prerequisites and updates prerequisites of previously-created Events
     * if applicable :
     * @param pName
     *              Name of Workshop
     * @param pDate
     *              Date of Workshop
     * @param pLocation
     *              Location of Workshop (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Ticket Price of Workshop (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Total number of Tickets for Workshop (may or may NOT contain a non-null value for 'ComingSoon' Event)
     *
     * @pre pName != null && pDate != null && pPrerequisites != null
     */
    public void addWorkshopEvent(String pName, LocalDate pDate, Location pLocation, Double pTicketPrice, Integer pNumTickets)
    {
        try {
            Event newEvent;
            if (pLocation == null || pTicketPrice == null || pNumTickets == null) { // To support 'ComingSoon' Events
                newEvent = eventCommand.addEvent_Workshop(pName,pDate,Optional.empty(),Optional.empty(),Optional.empty());
            } else {
                newEvent = eventCommand.addEvent_Workshop(pName, pDate, Optional.of(pLocation), Optional.of(pTicketPrice), Optional.of(pNumTickets));
            }
            if (newEvent == null) {
                System.out.println("An Event is already planned @"+pDate+" @"+pLocation);
            } else {
                aHostedEvents.add(newEvent);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input for addConcertEvent, please try again!");
        }
    }

    /**
     * Method to host a new Festival event with the following parameters :
     * @param pName
     *              Name of Festival.
     * @param pEvents
     *              List of all the Events happening during the Festival.
     * @pre pEvents != null
     */
    public void addFestival(String pName, List<Event> pEvents)
    {
        try {
            Event newEvent = eventCommand.addEvent_Festival(pName,pEvents);
            aHostedEvents.add(newEvent);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input for addConcertEvent, please try again!");
        }
    }


    /* ****************************************************************************************************************
                   USE SET PROFIT FUNCTIONS TO SET PROFIT-PER-TICKET (IN %) FOR SOME SPECIFIC EVENT TYPE
     **************************************************************************************************************** */
    /**
     * Set a new Concert - Profit Percentage and notify all Concert's instances created on EventBrite. (Observers)
     * @param pPercentage
     *              New concert-profit percentage.
     * @pre aHostedEvents != null && pPercentage != null &&  pPercentage >= 0 && pPercentage <= 100
     */
    public void setProfitConcert(Integer pPercentage) {
        try {
            calculator.setProfit(getHostedEvents(),Concert.class,pPercentage);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid Input for setProfitConcert :(");
        }
    }

    /**
     * Set a new Gala - Profit Percentage and notify all Gala's instances created on EventBrite. (Observers)
     * @param pPercentage
     *              New gala-profit percentage.
     * @pre aHostedEvents != null && pPercentage != null &&  pPercentage >= 0 && pPercentage <= 100
     */
    public void setProfitGala(Integer pPercentage) {
        try {
            calculator.setProfit(getHostedEvents(),Gala.class,pPercentage);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid Input for setProfitConcert :(");
        }
    }

    /**
     * Set a new Screening - Profit Percentage and notify all Screening's instances created on EventBrite (Observers)
     * @param pPercentage
     *              new screening-profit percentage
     * @pre aHostedEvents != null && pPercentage != null &&  pPercentage >= 0 && pPercentage <= 100
     */
    public void setProfitScreening(Integer pPercentage) {
        try {
            calculator.setProfit(getHostedEvents(),Screening.class,pPercentage);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid Input for setProfitConcert :(");
        }
    }

    /**
     * Set a new Workshop - Profit Percentage and notify all Screening's instances created on EventBrite (Observers)
     * @param pPercentage
     *              new workshop-profit percentage
     * @pre aHostedEvents != null && pPercentage != null &&  pPercentage >= 0 && pPercentage <= 100
     */
    public void setProfitWorkshop(Integer pPercentage) {
        try {
            calculator.setProfit(getHostedEvents(),Workshop.class,pPercentage);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid Input for setProfitConcert :(");
        }
    }

     /* ****************************************************************************************************************
         FOLLOWING METHODS SUPPORT FILTERING OF EVENTS HOSTED ON EVERBRITE :
            (1) Can Set a Filter ( 2 options : (1) Filter By Price Range (2) Filter By Location) which is
                added to the list of All Filters
     **************************************************************************************************************** */
    /**
     * Method used to filter all Events hosted on EverBrite using the List of all the filters to apply.
     * @return FilteredResult
     *              FilteredResult object that contains list of filtered Events.
     * @pre aHostedEvents != null && !aHostedEvents.isEmpty() && aFilters != null && !aFilter.isEmpty()
     */
    public FilteredResult filterHostedEvents() {
        try {
            return new FilteredResult(getHostedEvents(), aFilters);
        } catch(IllegalArgumentException e) {
            System.out.println("Filtering method was UNSUCCESSFUL due to invalid arguments!");
        }
        return null;
    }

    /**
     * Resets the list of filters to start "fresh" again.
     */
    public void resetFilters() {
        aFilters = new ArrayList<>();
    }

    /**
     * Method to set a specific Filter by Price range to take into account for the next
     * filtering action.
     * @param lowerBound
     *              lowest possible price value for our filter
     * @param upperBound
     *              upmost possible price value for our filter
     * @pre lowerBound != null && UpperBound != null && lowerBound >= 0 && UpperBound >= 0
     */
    public void setFilterByPriceRange(Double lowerBound,Double upperBound)
    {
        try {
            Filter filter = new Filter_ByPriceRange(lowerBound, upperBound);
            aFilters.add(filter);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid Input for setFilterByPriceRange :(");
        }
    }

    /**
     * Method to set a specific Filter by Location to take into account for the next
     * filtering action.
     * @param location
     *              location to match for filtering.
     * @pre location != null
     */
    public void setFilterByLocation(Location location)
    {
        try {
            Filter filter = new Filter_ByLocation(location);
            aFilters.add(filter);
        } catch(IllegalArgumentException e) {
            System.out.println("Invalid Input for setFilterByLocation :(");
        }
    }

    /* ****************************************************************************************************************
                                                    GETTER METHODS
     **************************************************************************************************************** */
    /**
     * Returns number of members in VIP List on EventBrite.
     */
    public int getNumOfVIPS() {
        if (aVIPs != null && !aVIPs.isEmpty()) {
            return aVIPs.size();
        }
        return 0;
    }

    /**
     * Returns copy of VIP List on EventBrite.
     */
   public List<String> getVIPs()
    {
        return new ArrayList<>(aVIPs);
    }

    /**
     * Add a VIP member to VIP List.
     * @param pName
     *          Name of VIP member.
     * @pre pName != null
     */
    public void addVIP(String pName)
    {
        if (pName != null) {
            aVIPs.add(pName);
        } else {
            System.out.println("Invalid Input for addVIP method :(");
        }
    }

    /**
     * Return the list of hosted events on EventBrite.
     * This method assumes that Events are immutable.
    */
    public ArrayList<Event> getHostedEvents(){
        return new ArrayList<>(aHostedEvents);
    }

    /**
     * Prints in order of creation all the events (ComingSoon & Concrete Events) hosted on EventBrite.
     */
    public void printHostedEvents() {
        int index = 1;
        System.out.println("Print Hosted Events : ");
        if (!aHostedEvents.isEmpty()) {
            for (Event event : aHostedEvents) {
                if (event.getLocation().isPresent()) {
                    System.out.print(index+" : <"+event.getName()+"> : @"+event.getDate()+" @"+event.getLocation().get()+"\n");
                } else {
                    System.out.print(index+" : 'ComingSoon' <"+event.getName()+"> : @"+event.getDate()+"\n");
                }
                index++;
            }
        }
        System.out.println();
    }
}






