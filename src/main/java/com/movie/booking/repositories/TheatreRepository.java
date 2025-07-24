package com.movie.booking.repositories;

import com.movie.booking.models.Movie;
import com.movie.booking.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    Optional<Theatre> findTheatreByName(String name);
}
