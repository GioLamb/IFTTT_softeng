import java.io.File;

public interface Action {
    public String getName();
    public String getContent1();
    public String getContent2();
    public void execute();
    public void onActionClose();
}


