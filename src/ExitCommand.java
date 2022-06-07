import java.io.Serializable;

public class ExitCommand implements Command, Serializable {
    @Override
    public void execute() {
        System.out.println("Программа завершена без сохранения в файл");
        //System.exit(1);
    }
}
