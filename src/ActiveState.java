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
        rule.changeState(new DeactiveState(rule));
    }

}
