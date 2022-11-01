import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Creates a Workshop Event with a name, a date, a location, a per-person ticket price
 * total number of tickets that can be issued and a list of existing Workshops that a
 * participant must have attended before attending this one.
 * The prerequisites are defined as all the Workshops with same Name, Location, Price, Number of tickets
 * and that came prior to the one being created.
 */
public class Workshop extends AbstractEvent {

    private List<Workshop> aPrerequisites; // list of all pre-existing Workshops that a participant must have attended

    /**
     * Creates a Workshop Event with the following parameters :
     * @param pName
     *              Name of Workshop
     * @param pDate
     *              Date of Workshop
     * @param pLocation
     *              Optional Location of Workshop (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pTicketPrice
     *              Optional Ticket Price of Workshop (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pNumTickets
     *              Optional Total number of Tickets for Workshop (may or may NOT contain a non-null value for 'ComingSoon' Event)
     * @param pPrerequisites
     *              List of all the Workshops hat a participant must have attended before attending this one.
     * @pre pPrerequisites != null
     */
    protected Workshop(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, List<Workshop> pPrerequisites) {
        super(pName, pDate, pLocation, pTicketPrice, pNumTickets);
        assert pPrerequisites != null;
        this.aPrerequisites=pPrerequisites;
    }

    /**
     * Add a Workshop to the prerequisite list of the current Workshop.
     * @param pWorkshop
     * @pre pWorkshop != null
     */
    public void addPrerequisite(Workshop pWorkshop) {
        assert pWorkshop != null;
        aPrerequisites.add(pWorkshop);
    }

    /**
     * @return List of all the prerequisites of the Workshop (OR empty List).
     */
    public List<Event> getPrerequisites() {
        if (!aPrerequisites.isEmpty()) {
            return new ArrayList<>(aPrerequisites);
        }
        return new ArrayList<>();
    }
}
