package com.movie.booking;

import com.movie.booking.models.Movie;
import com.movie.booking.models.Show;
import com.movie.booking.models.Theatre;
import com.movie.booking.repositories.MovieRepository;

import com.movie.booking.repositories.ShowRepository;
import com.movie.booking.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ShowRepository showRepository;

    @Autowired
    public DataInitializer(MovieRepository movieRepository ,  TheatreRepository theatreRepository, ShowRepository showRepository) {
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.showRepository = showRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {

            return args -> {

                movieRepository.saveAll(List.of(
                        new Movie(null, "The Dark Knight", "Action", "Batman faces the Joker in Gotham.", 2008L, 9.0, 152, List.of("Christian Bale", "Heath Ledger")),
                        new Movie(null, "Interstellar", "Sci-Fi", "Astronauts travel through a wormhole to save humanity.", 2014L, 8.6, 169, List.of("Matthew McConaughey", "Anne Hathaway")),
                        new Movie(null, "The Matrix", "Sci-Fi", "A hacker discovers reality is a simulation.", 1999L, 8.7, 136, List.of("Keanu Reeves", "Laurence Fishburne")),
                        new Movie(null, "Fight Club", "Drama", "An office worker creates an underground fight club.", 1999L, 8.8, 139, List.of("Brad Pitt", "Edward Norton")),
                        new Movie(null, "Pulp Fiction", "Crime", "Interwoven stories of crime in LA.", 1994L, 8.9, 154, List.of("John Travolta", "Samuel L. Jackson")),
                        new Movie(null, "Forrest Gump", "Drama", "A slow-witted man lives through historic events.", 1994L, 8.8, 142, List.of("Tom Hanks", "Robin Wright")),
                        new Movie(null, "The Shawshank Redemption", "Drama", "Two imprisoned men bond over decades.", 1994L, 9.3, 142, List.of("Tim Robbins", "Morgan Freeman")),
                        new Movie(null, "The Godfather", "Crime", "A mafia family's rise and fall.", 1972L, 9.2, 175, List.of("Marlon Brando", "Al Pacino")),
                        new Movie(null, "The Godfather Part II", "Crime", "Michael Corleone expands the family empire.", 1974L, 9.0, 202, List.of("Al Pacino", "Robert De Niro")),
                        new Movie(null, "Gladiator", "Action", "A general becomes a gladiator seeking revenge.", 2000L, 8.5, 155, List.of("Russell Crowe", "Joaquin Phoenix")),
                        new Movie(null, "The Prestige", "Drama", "Two magicians engage in a dangerous rivalry.", 2006L, 8.5, 130, List.of("Hugh Jackman", "Christian Bale")),
                        new Movie(null, "Whiplash", "Drama", "A young drummer battles a ruthless teacher.", 2014L, 8.5, 106, List.of("Miles Teller", "J.K. Simmons")),
                        new Movie(null, "Parasite", "Thriller", "A poor family infiltrates a wealthy household.", 2019L, 8.6, 132, List.of("Song Kang-ho", "Choi Woo-shik")),
                        new Movie(null, "Joker", "Crime", "The origin story of the infamous Gotham villain.", 2019L, 8.4, 122, List.of("Joaquin Phoenix", "Robert De Niro")),
                        new Movie(null, "The Avengers", "Superhero", "Earth's mightiest heroes unite to save the world.", 2012L, 8.0, 143, List.of("Robert Downey Jr.", "Chris Evans")),
                        new Movie(null, "Avengers: Endgame", "Superhero", "The Avengers attempt to reverse Thanos' snap.", 2019L, 8.4, 181, List.of("Robert Downey Jr.", "Chris Hemsworth")),
                        new Movie(null, "Titanic", "Romance", "A love story aboard the ill-fated Titanic.", 1997L, 7.9, 195, List.of("Leonardo DiCaprio", "Kate Winslet")),
                        new Movie(null, "Shutter Island", "Thriller", "A U.S. Marshal investigates a psychiatric facility.", 2010L, 8.2, 138, List.of("Leonardo DiCaprio", "Mark Ruffalo")),
                        new Movie(null, "The Wolf of Wall Street", "Biography", "The rise and fall of a stockbroker.", 2013L, 8.2, 180, List.of("Leonardo DiCaprio", "Jonah Hill")),
                        new Movie(null, "Django Unchained", "Western", "A freed slave sets out to rescue his wife.", 2012L, 8.4, 165, List.of("Jamie Foxx", "Christoph Waltz"))
                ));

                theatreRepository.saveAll(List.of(
                        new Theatre(null, "PVR Cinemas", "MG Road, Bangalore", 200),
                        new Theatre(null, "INOX", "Forum Mall, Bangalore", 150),
                        new Theatre(null, "Cinepolis", "Mantri Square, Bangalore", 180),
                        new Theatre(null, "Carnival Cinemas", "Koramangala, Bangalore", 120)
                ));

                showRepository.saveAll(List.of(

                        new Show(null,
                                movieRepository.findById(1L).get(),
                                theatreRepository.findById(1L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 17, 10, 0),
                                LocalDateTime.of(2025, 7, 17, 13, 0),
                                180
                        ),
                        new Show(null,
                                movieRepository.findById(1L).get(),
                                theatreRepository.findById(2L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 17, 14, 0),
                                LocalDateTime.of(2025, 7, 17, 17, 0),
                                190
                        ),
                        new Show(null,
                                movieRepository.findById(1L).get(),
                                theatreRepository.findById(3L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 18, 18, 30),
                                LocalDateTime.of(2025, 7, 18, 21, 30),
                                200
                        ),
                        new Show(null,
                                movieRepository.findById(2L).get(),
                                theatreRepository.findById(1L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 18, 10, 0),
                                LocalDateTime.of(2025, 7, 18, 13, 0),
                                220
                        ),
                        new Show(null,
                                movieRepository.findById(2L).get(),
                                theatreRepository.findById(2L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 19, 13, 30),
                                LocalDateTime.of(2025, 7, 19, 16, 30),
                                210
                        ),
                        new Show(null,
                                movieRepository.findById(2L).get(),
                                theatreRepository.findById(3L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 19, 17, 0),
                                LocalDateTime.of(2025, 7, 19, 20, 0),
                                230
                        ),
                        new Show(null,
                                movieRepository.findById(2L).get(),
                                theatreRepository.findById(4L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 20, 9, 0),
                                LocalDateTime.of(2025, 7, 20, 12, 0),
                                190
                        ),
                        new Show(null,
                                movieRepository.findById(1L).get(),
                                theatreRepository.findById(4L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 20, 15, 0),
                                LocalDateTime.of(2025, 7, 20, 18, 0),
                                200
                        ),
                        new Show(null,
                                movieRepository.findById(1L).get(),
                                theatreRepository.findById(2L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 21, 18, 0),
                                LocalDateTime.of(2025, 7, 21, 21, 0),
                                210
                        ),
                        new Show(null,
                                movieRepository.findById(2L).get(),
                                theatreRepository.findById(1L).get(),
                                0,
                                LocalDateTime.of(2025, 7, 21, 21, 30),
                                LocalDateTime.of(2025, 7, 22, 0, 30),
                                220
                        )


                ));


            };
    }
}
