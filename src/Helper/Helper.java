package Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static Helper helper = null;

    public static Helper getInstance() {
        if(helper == null) {
            helper = new Helper();
        }
        return helper;
    }

    public Boolean handleYesOrNoOption(String option) {
        Boolean output = false;
        if (option.equalsIgnoreCase("y")) {
            output = true;
        } else if (option.equalsIgnoreCase("n")) {
            output = false;
        }
        return output;
    }

    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy");
        return sdf.format(date);
    }
}
