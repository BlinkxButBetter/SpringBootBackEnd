package io.b3.Controllers;

import io.b3.Models.MovieRepository;
import io.b3.Models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mongodb.client.gridfs.model.GridFSFile;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        List<Movie> lst = movieRepository.findAll();
        System.out.println(lst);
        return lst;
    }

    @GetMapping("/movies/title/{title}")
    public Movie getMovieByTitle(@PathVariable String title) {
        return movieRepository.findByTitle(title);
    }
}
