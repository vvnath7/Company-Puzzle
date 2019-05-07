package starter;

public enum serviceEndpoints {
    BASEURL("http://api-aws-eu-qa-1.auto1-test.com"),
    MANUFACTURER("/v1/car-types/manufacturer"),
    CARTYPE("/v1/car-types/main-types"),
    BUILTYEAR("/v1/car-types/built-dates"),
    WA_KEY("coding-puzzle-client-449cc9d"); //base parameter
    private final String url;

    serviceEndpoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
