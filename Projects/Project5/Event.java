import java.time.LocalDate;
import java.util.Optional;

/*
Representation of a type of Event that can exist
 */
public interface Event {
    // To allow Bob to set Profit Percentage for each Event subclasses and notify corresponding instances of the update
    void updatePercentage(Integer pPercentage);
    boolean isEqual(Event pEvent);
    Double getProfit();

    String getName();
    LocalDate getDate();
    Optional<Location>  getLocation();
    Optional<Double> getPrice();
    Optional<Integer> getNumTickets();
    // N.B. Use Optional to allow the creation of 'ComingSoon' Events since the location + price + numTickets can be UNKNOWN
}
