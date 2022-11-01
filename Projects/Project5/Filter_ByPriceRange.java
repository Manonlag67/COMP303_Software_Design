import java.util.ArrayList;
import java.util.List;

/**
 * Creates a Filter by Price Range and filters out a List of Event based on the filter created / criteria to match.
 */
public class Filter_ByPriceRange implements Filter
{
    private Double aLowerBound; // lowest price selected
    private Double aUpperBound; // highest price selected

    /**
     * Creates a Filter corresponding to a Price Range to match.
     * @param pUpperBound
     *              Upper Bound of the price range of the filter being created.
     * @param pLowerBound
     *              Lower Bound of the price range of the filter being created.
     */
    public Filter_ByPriceRange(Double pLowerBound, Double pUpperBound) {
        if (pLowerBound == null || pUpperBound == null || pLowerBound < 0 || pUpperBound < 0) throw new IllegalArgumentException();
        this.aLowerBound = pLowerBound;
        this.aUpperBound = pUpperBound;
    }

    /**
     * @return list of events that have matched the filtering criteria
     */
    @Override
    public List<Event> filtering(List<Event> listToFilter)
    {
        if (listToFilter == null || listToFilter.isEmpty()) throw new IllegalArgumentException();
        List<Event> filteredList = new ArrayList<>();
        for (Event event: listToFilter) {
            if (test(event)) {
                filteredList.add(event);
            }
        }
        return filteredList;
    }

    /**
     * Test if an Event match the filtering criteria.
     * @param event
     *          Event to compare.
     * @pre event != null
     * @return TRUE if event match AND FALSE otherwise.
     */
    @Override
    public boolean test(Event event) {
        assert event != null;
        if (event.getLocation().isPresent()) {
            if (event.getPrice().get() >= aLowerBound && event.getPrice().get() <= aUpperBound) return true;
        }
        return false;
    }
}
