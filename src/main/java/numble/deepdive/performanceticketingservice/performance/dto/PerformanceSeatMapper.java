package numble.deepdive.performanceticketingservice.performance.dto;

import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
//@Mapper(componentModel = "spring")
public interface PerformanceSeatMapper {

    PerformanceSeatMapper INSTANCE = Mappers.getMapper(PerformanceSeatMapper.class);

    PerformanceSeatListResponse toDtoFromEntity(PerformanceSeat entity);
}
