package bootstrap.eventpubandlisten;

import bootstrap.domain.People;
import org.springframework.context.ApplicationEvent;

public class DemoEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DemoEvent(People source) {
        super(source);
    }

    public People getSource(){
        return (People)source;
    }


}
