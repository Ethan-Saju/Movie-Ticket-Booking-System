package com.movie.booking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Theatre theatre;

    private Integer seatsBooked = 0;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;


}

