package com.movie.booking.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Explicit join column for clarity
    @JsonBackReference(value = "user-ticket")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "show_id")
    @JsonBackReference(value = "show-ticket")
    private Show show;

    private Integer bookedSeats;

    private Double ticketPrice;
}

