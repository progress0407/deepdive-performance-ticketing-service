package numble.deepdive.performanceticketingservice.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VenueListResponses {

    private List<VenueListResponse> venues;
}
