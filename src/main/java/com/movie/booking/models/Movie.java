package com.movie.booking.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private String name;
    private String genre;
    private String description;
    private Long releaseYear;
    private Double rating;
    private Integer duration;

    @ElementCollection
    private List<String> starring = new ArrayList<>();

}

