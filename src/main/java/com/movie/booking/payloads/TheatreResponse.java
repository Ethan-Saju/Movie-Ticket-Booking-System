package com.movie.booking.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
public class TheatreResponse extends PaginatedResponse<TheatreDTO> {

}
