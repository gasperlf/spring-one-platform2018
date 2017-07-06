package test.pivotal.pal.tracker.timesheets;

import io.pivotal.pal.tracker.timesheets.TimesheetsApp;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class TimesheetsAppTest {

    @Test
    public void embedded() {
        TimesheetsApp.main(new String[]{});

        String response = new RestTemplate().getForObject("http://localhost:8181/time-entries?userId=0", String.class);

        assertThat(response).isEqualTo("[]");
    }
}
