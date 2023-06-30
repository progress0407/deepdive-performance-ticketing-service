package numble.deepdive.performanceticketingservice.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestSampleDataInitController {

    private final TestSampleDataInitService service;

    @PostMapping("/test/sample-data-init")
    public String testSampleDataInit() {

        service.testSampleDataInit("test-sample-data-1-others.sql");
        service.testSampleDataInit("test-sample-data-2-venue-seat-silverstone.sql");
        service.testSampleDataInit("test-sample-data-3-venue-seat-monaco.sql");

        return "test data init success";
    }
}
