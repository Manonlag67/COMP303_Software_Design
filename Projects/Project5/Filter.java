import java.util.List;

/*
 Representation of a Filter for Events
 */
public interface Filter {
    boolean test(Event event);
    List<Event> filtering(List<Event> aList);
}
