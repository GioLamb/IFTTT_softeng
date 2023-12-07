import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileAction implements Action {
    private String filePath;
    private String content;

    public WriteToFileAction(String filePath, String content) {
        this.filePath = filePath;
        this.content = content;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getContent1() {
        return null;
    }

    @Override
    public String getContent2() {
        return null;
    }



    @Override
    public void execute() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActionClose() {

    }
}
