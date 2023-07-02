package numble.deepdive.performanceticketingservice.support.mapper;

import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceSeatListResponse;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceSeatMapper;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class MapperTest {

    @Test
    void Entity클래스가_DTO로_변환되는지_확인한다() {
        // given
        Performance parentEntity = new Performance(1L, "", LocalDate.now(), LocalTime.now(), LocalTime.now(), 100, 200);
        VenueSeat venueSeat = new VenueSeat("A1", "GENERAL");
        PerformanceSeat entity = new PerformanceSeat(venueSeat, parentEntity);
        PerformanceSeatMapper mapper = PerformanceSeatMapper.INSTANCE;

        // when
        PerformanceSeatListResponse dto = mapper.toDtoFromEntity(entity);

        // then
        assertAll(
                () -> assertThat(entity.getId()).isEqualTo(dto.getId()),
                () -> assertThat(entity.getSeatNumber()).isEqualTo(dto.getSeatNumber()),
                () -> assertThat(entity.getSeatType()).isEqualTo(dto.getSeatType())
        );
    }

}