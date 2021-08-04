package Helper;

public class Helper {
    public Boolean handleYesOrNoOption(String option) {
        Boolean output = false;
        if (option.equalsIgnoreCase("y")) {
            output = true;
        } else if (option.equalsIgnoreCase("n")) {
            output = false;
        }
        return output;
    }
}
