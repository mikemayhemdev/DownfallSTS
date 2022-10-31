package collector.util.DuoUtils;

public class StringHelper {
    public static String highlightedText(String text) {
        return text.replaceAll("(?<=\\s|^)(?=\\S)", "#y");
    }
}
