import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

/**
 * The Add Event Command allows the creation of all types of Events (Concert / Gala / Workshop / Screening ),
 * Then, it compared the new Event Object with all the other previously-created-ones to avoid the creation
 * of Duplicates Events.
 */
public class Command_AddEvent {

    List<Event> aEvents = new ArrayList<>(); // List of all 'Unique' Events that have been created using the Event Management

    /**
     * Method to host a new concert event with NO DUPLICATES ALLOWED (= No previous Event created with same Location and Date)
     * @pre pName != null && pDate != null && pLocation != null && pArtist != null && pVIPs != null
     */
    public Event addEvent_Concert(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, String pArtist, List<String> pVIPs)
    {
        if (pName == null || pDate == null || pLocation == null || pTicketPrice == null || pNumTickets == null || pArtist == null || pVIPs == null) throw new IllegalArgumentException();
        Event newEvent = new Concert(pName,pDate,pLocation,pTicketPrice,pNumTickets,pArtist,pVIPs);

        if (!isPresent(newEvent)) { // Check if Event with same Location and Date has already been created
            aEvents.add(newEvent);
            return newEvent;
        }
        return null;
    }

    /**
     * Method to host a new Gala event with NO DUPLICATES ALLOWED (= No previous Event created with same Location and Date)
     * @pre pName != null && pDate != null && pLocation != null && pArtist != null && pVIPs != null
     */
    public Event addEvent_Gala(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, List<String> pVIPs)
    {
        if (pName == null || pDate == null || pLocation == null || pTicketPrice == null || pNumTickets == null || pVIPs == null) throw new IllegalArgumentException();
        Event newEvent = new Gala(pName,pDate,pLocation,pTicketPrice,pNumTickets,pVIPs);

        if (!isPresent(newEvent)) { // Check if Event with same Location and Date has already been created
            aEvents.add(newEvent);
            return newEvent;
        }
        return null;
    }

    /**
     * Method to host a new Screening event with NO DUPLICATES ALLOWED (= No previous Event created with same Location and Date)
     * @pre pName != null && pDate != null && pLocation != null && pArtist != null && pVIPs != null
     */
    public Event addEvent_Screening(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets, Screening.Rating pRating)
    {
        if (pName == null || pDate == null || pLocation == null || pRating == null) throw new IllegalArgumentException();
        Event newEvent = new Screening(pName,pDate,pLocation,pTicketPrice,pNumTickets,pRating);

        if (!isPresent(newEvent)) { // Check if Event with same Location and Date has already been created
            aEvents.add(newEvent);
            return newEvent;
        }
        return null;
    }

    /**
     * Method to host a new Workshop event with NO DUPLICATES ALLOWED + Automatic Prerequisites (= No previous Event created with same Location and Date)
     * @pre pName != null && pDate != null && pLocation != null && pArtist != null && pVIPs != null
     */
    public Event addEvent_Workshop(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets)
    {
        if (pName == null || pDate == null || pLocation == null) throw new IllegalArgumentException();

        List<Workshop> prerequisites = findPrerequisites(pName,pDate,pLocation,pTicketPrice,pNumTickets);

        Workshop newEvent = new Workshop(pName,pDate,pLocation,pTicketPrice,pNumTickets,prerequisites);
        updatePrerequisites(newEvent);

        if (!isPresent(newEvent)) { // Check if Event with same Location and Date has already been created
            aEvents.add(newEvent);
            return newEvent;
        }
        return null;
    }

    /**
     * Method to host a new Festival event with NO DUPLICATES ALLOWED
     * @pre pName != null && aEvents != null
     */
    public Event addEvent_Festival(String pName, List<Event> pEvents)
    {
        if (pName == null || pEvents == null) throw new IllegalArgumentException();
        Event newEvent = Festival.createFestival(pName,pEvents);
        if (!isPresent(newEvent)) {
            aEvents.add(newEvent);
            return newEvent;
        }
        return null;
    }

    /**
     * Find all possible prerequisites of a new Workshop being created by going through
     * th list of all Event created and match any other Workshop with same Name, same Location, and ulterior Date.
     * @param pName
     *          Name of Workshop being Created
     * @param pDate
     *          Date of Workshop being Created
     * @param pLocation
     *          Location of Workshop being Created
     * @return list of all Workshop events that have matched as prerequisites of the Workshop being created
     */
    private List<Workshop> findPrerequisites(String pName, LocalDate pDate, Optional<Location> pLocation, Optional<Double> pTicketPrice, Optional<Integer> pNumTickets)
    {
        assert pName != null && pDate != null && pLocation != null;
        List<Workshop> aPrerequisites = new ArrayList<>();

        if (!aEvents.isEmpty()) {
            for (Event event : aEvents) {
                if (event instanceof Workshop) {
                    if (event.getLocation().isPresent() && event.getPrice().isPresent() && event.getNumTickets().isPresent()) {
                        if (event.getName().equals(pName) && event.getDate().isBefore(pDate) && event.getLocation().get().equals(pLocation.get()) && event.getPrice().get().equals(pTicketPrice.get()) && event.getNumTickets().get().equals(pNumTickets.get()))  {
                            aPrerequisites.add((Workshop)event);
                        }
                    }
                }
            }
        }
        return aPrerequisites;
    }

    /**
     * Update (if applicable) the prerequisites list of all the Workshop hosted on EventBrite
     * if a Workshop being created is a prerequisites of one.
     * @param pWorkshop
     *              Workshop Event being created
     */
    private void updatePrerequisites(Workshop pWorkshop) {
        assert pWorkshop != null;
        if (pWorkshop !=null && !aEvents.isEmpty() && pWorkshop.getLocation().isPresent() && pWorkshop.getPrice().isPresent() && pWorkshop.getNumTickets().isPresent()) {
            for (Event event : aEvents) {
                if (event instanceof Workshop) {
                    if (event.getLocation().isPresent() && event.getNumTickets().isPresent() && event.getPrice().isPresent()) {
                        if (event.getName().equals(pWorkshop.getName()) && event.getDate().isAfter(pWorkshop.getDate()) && event.getLocation().get().equals(pWorkshop.getLocation().get()) && event.getPrice().get().equals(pWorkshop.getPrice().get()) && event.getNumTickets().get().equals(pWorkshop.getNumTickets().get())) {
                            ((Workshop) event).addPrerequisite(pWorkshop);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method that check if an Event (w/ same date and location) has been hosted already.
     * @param pEvent
     * @pre newEvent != null
     */
    private boolean isPresent(Event pEvent)
    {
        assert pEvent != null;
        for(Event event : aEvents)
        {
            if (event.isEqual(pEvent)) {
                return true;
            }
        }
        return false;
    }
}
