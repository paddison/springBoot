package com.amigoscode.movie;

import com.amigoscode.actor.Actor;
import com.amigoscode.actor.ActorDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieDataAccessService implements MovieDao {

    private final JdbcTemplate jdbcTemplate;
    private final ActorDao actorDao;

    public MovieDataAccessService(JdbcTemplate jdbcTemplate, ActorDao actorDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.actorDao = actorDao;
    }

    @Override
    public List<Movie> selectMovies() {
        final String sql = """
                SELECT m.id, m.name, release_date FROM movie m 
                LIMIT 100;
                """;
        return jdbcTemplate.query(sql, new MovieRowMapper(jdbcTemplate));
    }

    @Override
    public int insertMovie(Movie movie) {
        List<Actor> actors = movie.actors();

        final String sql = """
                INSERT INTO movie(name, release_date) VALUES (?, ?);
                """;
        int result = jdbcTemplate.update(sql, movie.name(), movie.releaseDate()); // update returns number of rows changed

        Optional<Movie> insertedMovie = selectMovies().stream().min(new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.id() - o1.id();
            }
        });

        if (actors.size() > 0) {
            long updatedRows = actors.stream().map((actor -> actorDao.insertActor(actor, insertedMovie.get()))).reduce(0, Integer::sum);
            if (actors.size() != updatedRows) {
                throw new IllegalStateException("Couldn't insert actors into database");
            }
        }

        return result;
    }

    @Override
    public int deleteMovie(int id) {
        final String sql = """
                DELETE FROM movie WHERE id = ?;
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        final String sql = """
                SELECT id, name, release_date FROM movie WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, new MovieRowMapper(jdbcTemplate), id).stream().findFirst();
    }

    @Override
    public int updateMovie(int id, Movie movie) {
        final String sql = """
                UPDATE movie SET name = ?, release_date = ? WHERE id = ?;
                """;
        return jdbcTemplate.update(sql, movie.name(), movie.releaseDate(), id);
    }

}
