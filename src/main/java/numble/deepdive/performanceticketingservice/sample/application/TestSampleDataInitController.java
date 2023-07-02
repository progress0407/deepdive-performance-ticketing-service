package numble.deepdive.performanceticketingservice.sample.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.deepdive.performanceticketingservice.sample.presentation.TestSampleDataInitService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * see test package's ProductTestSampleDataInitUtils
 */
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

        service.testSampleDataInit("test-sample-data-4-performance-seat-1.sql");
        service.testSampleDataInit("test-sample-data-5-performance-seat-2.sql");
        service.testSampleDataInit("test-sample-data-6-performance-seat-3.sql");

        return "test data init success";
    }
}
