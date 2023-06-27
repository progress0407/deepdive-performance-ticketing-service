package numble.deepdive.performanceticketingservice.venue.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

import java.util.Set;
import java.util.stream.Collectors;

public record VenueCreateRequest(@NotBlank String name, @Valid @NotEmpty Set<SeatCreateRequest> seats) {

    public Venue toEntity() {

        Set<VenueSeat> convertedSeats = seats.stream()
                .map(SeatCreateRequest::toEntity)
                .collect(Collectors.toSet());

        return new Venue(name, convertedSeats);
    }
}

