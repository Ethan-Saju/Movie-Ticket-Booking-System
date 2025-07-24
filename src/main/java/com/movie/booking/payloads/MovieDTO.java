package com.movie.booking.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class MovieDTO{

    private Long movieId;
    private String name;
    private String genre;
    private String description;
    private Long releaseYear;
    private Double rating;
    private Integer duration;
    private List<String> starring = new ArrayList<>();

}
