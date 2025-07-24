package com.movie.booking.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {

    private Long showId;

    private Long movieId;
    private String movieName;

    private Long theatreId;
    private String theatreName;

    private Integer seatsBooked;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;
}

