package data.remote;

public class ApiUtils {
    public static final String BASE_URL = "https://demo4110086.mockable.io/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
