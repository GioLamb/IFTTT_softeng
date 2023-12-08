import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DeactiveState extends Context{
    public DeactiveState(Rule rule) {
        super(rule);
    }

    @Override
    public void deactivate(){
        return;
    }

    @Override
    public void activate(){
        rule.changeState(new ActiveState(rule));
    }
}