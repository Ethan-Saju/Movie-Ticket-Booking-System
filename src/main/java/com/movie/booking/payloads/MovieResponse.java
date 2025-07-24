package com.movie.booking.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;




@Data
@EqualsAndHashCode(callSuper = true)
public class MovieResponse extends PaginatedResponse<MovieDTO> {


}