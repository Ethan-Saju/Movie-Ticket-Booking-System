package com.movie.booking.repositories;

import com.movie.booking.models.Movie;
import com.movie.booking.models.Show;
import com.movie.booking.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovie(Movie movie);
    List<Show> findByTheatre(Theatre theatre);

    @Query("""
        SELECT show 
        FROM Show show 
        WHERE show.theatre = :theatre 
          AND :startTime < show.endTime 
          AND :endTime > show.startTime
    """)
    List<Show> findOverlappingShows(
            @Param("theatre") Theatre theatre,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    void deleteAllByTheatre(Theatre theatre);

    void deleteAllByMovie(Movie movie);
}
