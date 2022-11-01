import java.util.ArrayList;
import java.util.List;

/**
 * The Profit Calculator Command supports different functions for the computation of the Total
 * Expected Profit of a filtered Result object :
 *      (1) To Set a 'customized' Profit-per-Ticket Percentage for a specific event type.
 *      (2) To Notify all Events hosted on EventBrite of that certain type.
 */
public class Command_ProfitCalculator {

    private List<Event> aEvents = new ArrayList<>(); // List of All Events hosted on EverBrite
    private Integer aPercentage;

    /**
     * Set a new Profit-per-Ticket Percentage for a specific event type and notify all its instances
     * created on EventBrite.
     * @param pPercentage
     *              new profit percentage
     * @pre className != null && pPercentage != null &&  pPercentage >= 0 && pPercentage <= 100
     */
    public <T> void setProfit(List<Event> pEvents, Class<T> className, Integer pPercentage) throws IllegalArgumentException{
        if (pEvents == null || pEvents.isEmpty() || className  == null || pPercentage == null) throw new IllegalArgumentException();
        if (pPercentage >= 0 && pPercentage <= 100) {
            this.aEvents=pEvents;
            this.aPercentage=pPercentage;
            notifyObservers(className);
        } else {
            System.out.println("Input for setProfitConcert is invalid: "+pPercentage+" Please choose value between 0-100");
        }
    }

    /**
     * Method to notify all the Observers of an update of Percentage for specific class.
     * @param className
     *              A specific class type.
     * @pre className != null && !aEvents.isEmpty() && aPercentage != null &&  0 <= aPercentage <=100
     */
    private <T> void notifyObservers(Class<T> className) {
        assert className != null;
        for (Event event : aEvents) {
            if (event.getClass().equals(className)) event.updatePercentage(aPercentage);
        }
    }
}
