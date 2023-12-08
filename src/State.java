import javafx.beans.property.BooleanProperty;

public interface State {
    public void activate();
    public void deactivate();

    BooleanProperty get();
}