package com.movie.booking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(columnList = "name", unique = true))
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @NotBlank(message = "Movie name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Genre is required")
    @Size(min = 3, max = 50, message = "Genre must be between 3 and 50 characters")
    @Column(nullable = false)
    private String genre;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @NotNull(message = "Release year is required")
    @Min(value = 1900, message = "Release year must be 1900 or later")
    @Max(value = 2100, message = "Release year cannot be in the far future")
    @Column(nullable = false)
    private Long releaseYear;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10.0")
    @Column(columnDefinition = "DECIMAL(3,1) DEFAULT 0.0")
    private Double rating = 0.0;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 300, message = "Duration cannot exceed 300 minutes")
    @Column(nullable = false)
    private Integer duration;

    @ElementCollection
    @Size(max = 20, message = "Cannot have more than 20 actors/actresses listed")
    private List<@NotBlank(message = "Actor/Actress name cannot be blank") String> starring = new ArrayList<>();

}
