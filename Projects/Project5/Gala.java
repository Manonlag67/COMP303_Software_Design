import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create a Gala Event with a name, a date, a location, a per-person ticket price
 * total number of tickets that can be issued and a specific list of VIPs.
 */
public class Gala extends AbstractEvent
{
    private List<String> aVIPs; // list of VIPs for the party

    /**
     * Creates a Gala Event with the following parameters :
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
     * @pre pArtist != null
     */
    protected Gala(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, List<String> pVIPs) {
        super(pName, pDate, pLocation, pTicketPrice, pNumTickets);
        assert pVIPs != null;
        this.aVIPs=pVIPs;
    }

    public List<String> getVIPs() {
        return new ArrayList<>(aVIPs);
    }
}
