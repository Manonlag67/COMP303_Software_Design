import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

/**
 * Creates a Festival Event, a type of event in which multiple events can happen over a period of time
 * with the following requirements :
 *      * (1) FESTIVAL'S TICKET PRICE = 20% of the price of all the events at the festival together
 *      * (2) TOTAL NUMBER OF TICKET = # of tickets of the Event with the least number of tickets
 *      * (3) LOCATION = depends on the location of events within the Festival
 *      * (4) FESTIVAL'S DATE = date of the first event that will take place in the festival
 *      * (5) Once created, the list of event CANNOT be changed
 */
public class Festival extends AbstractEvent
{
    final private List<Event> aEvents; // IMMUTABLE LIST of events in a Festival (can not be changed!) = REQUIREMENT (5)

    /**
     * Creates and returns a new Festival Event with the following properties :
     * @param pName
     *          Name of Festival
     * @param pEvents
     *          List of events organized for the Festival
     * @return Festival created after making sure it meets all requirements
     */
    public static Event createFestival(String pName, List<Event> pEvents)
    {
        assert pName != null && pEvents != null && !pEvents.isEmpty();

        LocalDate festival_Date = null;
        Location festival_Location = null;
        Double festival_TicketPrice = 0.0;
        Integer festival_NumTickets = Integer.MAX_VALUE;

        for(Event event : pEvents)
        {
            // (4) : FESTIVAL'S DATE = date of the first event that will take place in the festival
            LocalDate date = event.getDate(); // Every EVENT have a Name + Date

            if (festival_Date == null) festival_Date=date;
            if (date.isBefore(festival_Date)) festival_Date=date;

            // (3) : LOCATION = depends on the location of events within the Festival
            Optional<Location> location = event.getLocation();
            if (location.isPresent()) { // Check = Concrete Event OR 'ComingSoon' Event
                if (festival_Location == null) festival_Location = location.get();
                else {
                    if (festival_Location != location.get()) festival_Location= Location.Multiple;
                }
            }
            // (1) : FESTIVAL'S TICKET PRICE = 20% of the price of all the events at the festival together :
            Optional<Double> price = event.getPrice();
            if (price.isPresent()) { // Check = Concrete Event OR 'ComingSoon' Event
                festival_TicketPrice += price.get()*0.2;
            }

            // (2) : TOTAL NUMBER OF TICKET = # of tickets of the Event with the least number of tickets
            Optional<Integer> capacity = event.getNumTickets();
            if (capacity.isPresent()) { // Check = Concrete Event OR 'ComingSoon' Event
                if (festival_NumTickets > capacity.get()) festival_NumTickets=capacity.get();
            }
        }
        return new Festival(pName,festival_Date,Optional.of(festival_Location),Optional.of(festival_TicketPrice),Optional.of(festival_NumTickets), pEvents);
    }

    /**
     * Creates Festival that respects all requirements mentioned above with the following parameters :
     * @param pName
     *              Name of Festival.
     * @param pDate
     *              Date of Festival.
     * @param pLocation
     *              Optional Location of Festival (may or may NOT contain a non-null value for 'ComingSoon' Event).
     * @param pTicketPrice
     *              Optional Ticket Price of Festival (may or may NOT contain a non-null value for 'ComingSoon' Event).
     * @param pNumTickets
     *              Optional Total number of Tickets for Festival (may or may NOT contain a non-null value for 'ComingSoon' Event).
     * @param pEvents
     *              List of all the Events happening during the Festival.
     * @pre pEvents != null
     */
    private Festival(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, List<Event> pEvents) {
        super(pName,pDate,pLocation,pTicketPrice,pNumTickets);
        assert pEvents != null;
        this.aEvents = pEvents;
    }

    @Override
    public Double getProfit(){
        Double profit = 0.0;
        for (Event event : aEvents) {
            profit += event.getProfit();
        }
        return profit;
    }

    /**
     * @return Festival's list of Events
     */
    public List<Event> getFestivalEventsList() {
        return new ArrayList<>(aEvents);
    }
}
