package service.interfaces;

import domain.Genre;
import utils.OnlineLibraryException;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre get(Long id);
    void add(Genre genre);
    void edit(Genre genre);
    void delete(Long id) throws OnlineLibraryException;

}