import java.util.ArrayList;
import java.util.List;

/**
 * Filtered Result contains a list of events that have been filtered using a specific FilterCommand.
 * For now, Bob is allowed to filter events based on either a price range of the event,
 * or at a particular location.
 */
public class FilteredResult {

    private List<Event> aFilteredEvents; // list of all the Events that have matched the filtering criteria

    /**
     * Creates a list of events that have been filtered using the list of filters one-by-one.
     * @param allEvents
     *              List of Events to filter.
     * @param allFilters
     *              List of Filters that will be used for the filtering.
     */
    public FilteredResult(List<Event> allEvents, List<Filter> allFilters) {
        if (allEvents == null || allEvents.isEmpty() || allFilters == null || allFilters.isEmpty()) throw new IllegalArgumentException();
        this.aFilteredEvents = allEvents;
        for (Filter filter : allFilters) {
            aFilteredEvents = filter.filtering(aFilteredEvents);
        }
    }

    /**
     * @return the expected profit of the filtered list.
     * @pre aFilteredEvents != null && !aFilteredEvents.isEmpty()
     */
    public Double getExpectedProfit() {
        Double TotalProfit = 0.0;

        for (Event event : aFilteredEvents) {
            TotalProfit+=event.getProfit();
        }
        return TotalProfit/100;
    }

    /**
     * EXTRA METHOD FOR PRINTING :
     * Prints in order of creation all the events (ComingSoon & Concrete Events) hosted on EventBrite.
     */
    public void printFilteredResult() {
        int index = 1;
        System.out.println("Print Filtered Result : ");
        if (!aFilteredEvents.isEmpty()) {
            for (Event event : aFilteredEvents) {
                if (event.getLocation().isPresent() && event.getPrice().isPresent() && event.getLocation().isPresent() && event.getNumTickets().isPresent()) {
                    System.out.print(index+" : <"+event.getName()+"> : @"+event.getDate()+" @"+event.getLocation().get()+" Ticket's price: "+event.getPrice().get()+" Number of Tickets: "+event.getNumTickets().get()+"\n");
                }
                index++;
            }
        }
        System.out.println();
    }
}
