package com.movie.booking.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class TheatreDTO {
    private Long theatreId;
    private String name;
    private String address;
    private Integer capacity;
}
