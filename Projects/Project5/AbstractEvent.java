import java.time.LocalDate;
import java.util.Optional;

/**
 * Abstraction to Represent an Event with a Name, a Date, a Location, a Ticket Price and a number of Tickets.
 * This abstraction also supports the creation of 'ComingSoon' Event with a Name and a Date ONLY by using
 * the Optional Type for all values that may (for 'ComingSoon' Events) or may not be NON-NULL (for Concrete
 * Events).
 */
public class AbstractEvent implements Event
{
    // Name and Date are immutable fields for all Events.
    final private String aName;
    final private LocalDate aDate;
    // N.B. Use Optional to support the creation of 'Coming Soon' event w/ UNKNOWN Location, TicketPrice,
    // and number of Tickets.
    private Optional<Location> aLocation;
    private Optional<Double> aTicketPrice;
    private Optional<Integer> aNumTickets;

    private Integer aPercentage = 20; // Default Percentage = 20% profit percentage per ticket.

    /**
     * Creates an Event with the following parameters :
     * @param pName
     *              Name of the Event hosted on EventBrite
     * @param pDate
     *              Date of the Event
     * @param pLocation
     *              Optional Location of the Event (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Optional Ticket Price per person for the Event (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Optional Total number of tickets that can be issued for the Event (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @pre pName != null && pDate != null
     */
    // N.B. Declare constructor w/ protected modifier to allow subclasses access to them
    protected AbstractEvent(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets)
    {
        assert pName != null && pDate != null;
        this.aName=pName;
        this.aDate=pDate;
        this.aLocation=pLocation;
        this.aTicketPrice=pTicketPrice;
        this.aNumTickets=pNumTickets;
    }

    /**
     * Updates the Profit Percentage for an Event with the percentage method argument.
     * @param pPercentage
     *              new Percentage to update for the Event
     * @pre pPercentage != null && pPercentage >= 0 && pPercentage<=100
     */
    @Override
    public void updatePercentage(Integer pPercentage) {
        assert pPercentage != null || pPercentage >= 0 || pPercentage<=100;
        this.aPercentage = pPercentage;
    }

    /**
     * Verify Equality between two Events. They are equals when
     * they share the same Date and Location.
     * @param pEvent
     *              Event to compare
     * @return TRUE if the two event are equals, FALSE otherwise
     * @pre newEvent != null
     */
    @Override
    public boolean isEqual(Event pEvent) {
        assert pEvent != null;
        if (aDate.isEqual(pEvent.getDate()) && aLocation.equals(pEvent.getLocation())) return true;
        return false;
    }

    /**
     * Computes the expected Profit per-ticket for Concrete Events
     * and return 0 for 'ComingSoon' Events.
     * @return profit of Event computed using the following formula : { aTicketPrice * aNumTickets * aPercentage }
     */
    @Override
    public Double getProfit() {
        if (aTicketPrice.isPresent() && aNumTickets.isPresent()) { // Check that necessary values are present a.k.a. Concrete Event!
            return aTicketPrice.get() * aNumTickets.get() * aPercentage;
        }
        return 0.0; // 'ComingSoon' Event
    }

    /**
     * @return The Name of the Event.
     */
    @Override
    public String getName() {
        return aName;
    }
    /**
     * @return The Date of the Event.
     */

    @Override
    public LocalDate getDate() {
        return aDate;
    }

    /**
     * @return The Location of the Event, or Optional.empty() for 'ComingSoon' Event.
     */
    @Override
    public Optional<Location> getLocation() {
        return aLocation;
    }

    /**
     * @return The Price per ticket for the Event, or Optional.empty() for 'ComingSoon' Event.
     */
    @Override
    public Optional<Double> getPrice() {
        return aTicketPrice;
    }

    /**
     * @return The total number of Tickets, or Optional.empty() for 'ComingSoon' Event.
     */
    @Override
    public Optional<Integer> getNumTickets() {
        return aNumTickets;
    }
}
