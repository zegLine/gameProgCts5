package test_chatgame;

public class Commands {

    public static void buy(String[] parameters) {
        if (parameters.length >= 1) {
            String itemToBuy = parameters[0];
            System.out.println("Buying " + itemToBuy);
        } else {
            System.out.println("Usage: buy <item>");
        }
    }

}
