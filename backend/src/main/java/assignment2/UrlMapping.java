package assignment2;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOK = API_PATH + "/books";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USER = API_PATH + "/user";

    public static final String ENTITY = "/{id}";

    public static final String SELL = "/sell-book";

    public static final String FILTER = "/filter";
}
