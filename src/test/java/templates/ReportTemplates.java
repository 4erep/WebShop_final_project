package templates;

import io.qameta.allure.restassured.AllureRestAssured;

public class ReportTemplates {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    private static class InitLogFilter {
        private static final ReportTemplates logFilter = new ReportTemplates();
    }

    private ReportTemplates() {}

    public static ReportTemplates filters() {
        return InitLogFilter.logFilter;
    }

    public AllureRestAssured customTemplates() {
        FILTER.setRequestTemplate("request.ftl");
        FILTER.setResponseTemplate("response.ftl");
        return FILTER;
    }
}
