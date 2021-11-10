package com.amigoscode.actor;

import com.amigoscode.movie.Movie;

import java.util.List;
import java.util.Optional;

public interface ActorDao {
    List<Actor> selectActorsByMovieId(Integer movieId);
    int insertActor(Actor actor, Movie movie);
    int deleteActor(int id);
    Optional<Movie> selectActorById(int id);
    int updateActor(int id, Movie movie);
}
