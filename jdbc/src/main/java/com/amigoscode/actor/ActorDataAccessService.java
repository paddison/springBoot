package com.amigoscode.actor;

import com.amigoscode.movie.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActorDataAccessService implements ActorDao{

    private final JdbcTemplate jdbcTemplate;

    public ActorDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Actor> selectActorsByMovieId(Integer movieId) {
        final String sql = "SELECT * FROM actor WHERE movie = ?;";
        return jdbcTemplate.query(sql, new ActorRowMapper(), movieId);
    }

    @Override
    public int insertActor(Actor actor, Movie movie) {
        final String sql = """
                INSERT INTO actor(name, movie) VALUES (?, ?);
                """;
        return jdbcTemplate.update(sql, actor.name(), movie.id());
    }

    @Override
    public int deleteActor(int id) {
        return 0;
    }

    @Override
    public Optional<Movie> selectActorById(int id) {
        return Optional.empty();
    }

    @Override
    public int updateActor(int id, Movie movie) {
        return 0;
    }
}
