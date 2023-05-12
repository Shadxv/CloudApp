package pl.shadxw.api.util;

import java.util.List;
import java.util.regex.Pattern;

public class StringUtilities {

    public static String parse(final String msg) {
        Pattern colorPattern = Pattern.compile("&([0-9a-fk-or])");

        if (msg == null)
            return null;

        if (msg.isEmpty())
            return "";

        return colorPattern.matcher(msg).replaceAll("\u00a7$1");
    }

    public static String combineList(List<String> list) {
        if (list == null)
            return "";

        if (list.size() == 0)
            return "";

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : list)
            stringBuilder.append(s).append("\\n");

        return stringBuilder.toString();
    }

}
