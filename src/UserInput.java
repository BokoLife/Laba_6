import java.util.Scanner;

public class UserInput {

    public static void start(){
        while (true) {
            Scanner scan = new Scanner(System.in);
            String userInput = scan.nextLine();
            Laba_5.userInputMas = userInput.split(" ", 3);
            if (Laba_5.commands.getKeys().contains(Laba_5.getCommand())) {
                Laba_5.commands.get(Laba_5.getCommand()).execute();
                Laba_5.commandsHistory.add(Laba_5.getCommand());
            } else {
                System.out.println("Было введено значение не из списка команд");
            }
            Laba_5.userInputMas = new String[2];
        }
    }
}
