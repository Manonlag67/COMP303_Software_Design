import java.time.LocalDate;
import java.util.Optional;

/**
 * Creates a Screening Event with a name, a date, a location, a per-person ticket price
 * total number of tickets that can be issued and a specific Rating.
 */
public class Screening extends AbstractEvent {

    public enum Rating {G,PG,PG_13,R};
    private Rating aRating; // Rating of the Movie screening

    /**
     * Creates a Screening Event with the following parameters :
     * @param pName
     *              Name of Screening
     * @param pDate
     *              Date of Screening
     * @param pLocation
     *              Optional Location of Screening (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Optional Ticket Price of Screening (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Optional Total number of Tickets for Screening (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pRating
     *              Rating of Screening
     * @pre pArtist != null
     */
    protected Screening(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, Rating pRating) {
        super(pName, pDate, pLocation, pTicketPrice, pNumTickets);
        assert pRating != null;
        this.aRating=pRating;
    }

    public Rating getRating()
    {
        return aRating;
    }
}
