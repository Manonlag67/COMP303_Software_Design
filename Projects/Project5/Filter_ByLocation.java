import java.util.ArrayList;
import java.util.List;

/**
 * Creates a Filter by Location and filters out a List of Event based on the filter created / criteria to match.
 */
public class Filter_ByLocation implements Filter
{
    private Location aLocation;// criteria to match

    /**
     * Creates a Filter corresponding to a Location to match.
     * @param pLocation
     *              Location to match
     * @pre pLocation != null
     */
    public Filter_ByLocation(Location pLocation) {
        if (pLocation == null) throw new IllegalArgumentException();
        this.aLocation = pLocation;
    }

    /**
     * Main Method used to filter a list of Events using the Object filter that
     * is associated with a criteria to meet.
     * @return list of events that have matched the filtering criteria
     * @pre listToFilter != null && !listToFilter.isEmpty()
     */
    @Override
    public List<Event> filtering(List<Event> listToFilter)
    {
        if (listToFilter == null || listToFilter.isEmpty()) throw new IllegalArgumentException();
        List<Event> filteredList = new ArrayList<>();
        for (Event event: listToFilter) {
            if (test(event)) { // event CANNOT BE NULL
                filteredList.add(event);
            }
        }
        return filteredList;
    }

    /**
     * Tests if an Event match the filtering criteria.
     * @param event
     *          Event to compare.
     * @return TRUE if event match AND FALSE otherwise.
     * @pre event != null
     */
    @Override
    public boolean test(Event event) {
        assert event != null;
        if (event.getLocation().isPresent()) {
            if (event.getLocation().get().equals(aLocation)) return true;
        }
        return false;
    }
}
