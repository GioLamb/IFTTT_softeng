import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ActiveState extends Context{
    public ActiveState(Rule rule) {
        super(rule);
    }

    @Override
    public void activate(){
        return;
    }
    @Override
    public void deactivate(){
        rule.setState(new DeactiveState(rule));
    }

    @Override
    public BooleanProperty get() {
        return new SimpleBooleanProperty(true);
    }

}
