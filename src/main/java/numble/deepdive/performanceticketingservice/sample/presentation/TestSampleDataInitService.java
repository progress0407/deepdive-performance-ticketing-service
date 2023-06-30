package numble.deepdive.performanceticketingservice.sample.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestSampleDataInitService {

    private final JdbcTemplate jdbcTemplate;

    private final VenueRepository venueRepository;

    public void testSampleDataInit(String path) {

        Resource resource = loadSqlFile(path);
        String fullSqls = convertResourceToString(resource);
        String[] sqlStatements = splitFullSqls(fullSqls);
        executeEachStatement(sqlStatements);

        Venue all = venueRepository.findAggregateByIdOrThrow(20001);
        log.info("{}", all);
    }

    private static ClassPathResource loadSqlFile(String path) {

        return new ClassPathResource(path);
    }

    private void executeEachStatement(String[] sqlStatements) {
        for (String statement : sqlStatements) {
            jdbcTemplate.execute(statement);
        }
    }

    private static String convertResourceToString(Resource resource) {
        return new String(getBytes(resource), StandardCharsets.UTF_8);
    }

    private static String[] splitFullSqls(String fullSqls) {
        return fullSqls.replaceAll("[\r\n]", "").split(";");
    }

    private static byte[] getBytes(Resource resource) {
        try {
            return FileCopyUtils.copyToByteArray(resource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
