package com.amigoscode.movie;

import com.amigoscode.actor.ActorDataAccessService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public record MovieRowMapper(JdbcTemplate jdbcTemplate) implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {

        int id = rs.getInt("id");
        return new Movie(
                id,
                rs.getString("name"),
                new ActorDataAccessService(jdbcTemplate).selectActorsByMovieId(id),
                LocalDate.parse(rs.getString("release_date"))
        );
    }
}
