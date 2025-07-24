package com.movie.booking.repositories;

import com.movie.booking.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long>{
    Optional<AppUser> findByEmail(String email);

}
