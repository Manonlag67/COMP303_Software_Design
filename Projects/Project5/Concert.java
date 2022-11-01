import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Creates a Concert with a name, a date, a location, a per-person ticket price
 * total number of tickets that can be issued, a specific Artist and list of VIPs.
 */
public class Concert extends AbstractEvent
{
    // Specific Attributes of Concert :
    private String aArtist; // Name of the artist who is performing at the concert
    private List<String> aVIPs; // List of all the "VIPs" invited ot the concert

    /**
     * Creates a Concert Event with the following parameters :
     * @param pName
     *              Name of Concert
     * @param pDate
     *              Date of Concert
     * @param pLocation
     *              Optional Location of Concert (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Optional Ticket Price of Concert (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Optional Total number of Tickets for Concert (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pArtist
     *              Artist of Concert
     * @param pVIPs
     *              list of VIPS for the Concert
     * @pre pArtist != null
     */
    public Concert(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, String pArtist, List<String> pVIPs)
    {
        super(pName,pDate,pLocation,pTicketPrice,pNumTickets);
        assert pArtist != null && pVIPs != null;
        this.aArtist=pArtist;
        this.aVIPs=pVIPs;
    }

    /**
     * @return Artist playing at Concert
     */
    public String getArtist()
    {
        return aArtist;
    }

    /**
     * @return list of VIPS of Concert
     */
    public List<String> getVIPs()
    {
        if (aVIPs != null && !aVIPs.isEmpty()) {
            return new ArrayList<>(aVIPs);
        }
        return null;
    }
}
