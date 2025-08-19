package com.eli.bims.config;

import com.eli.bims.dao.Author;
import com.eli.bims.dao.Book;
import com.eli.bims.dao.Genre;
import com.eli.bims.dao.Publisher;
import com.eli.bims.dto.request.CreateBookRequest;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    private final Converter<Long, Genre> toGenre = (ctx) -> {
        if (ctx.getSource() == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(ctx.getSource());
        return genre;
    };
    private final Converter<Long, Publisher> toPublisher = (ctx) -> {
        if (ctx.getSource() == null) {
            return null;
        }
        Publisher publisher = new Publisher();
        publisher.setId(ctx.getSource());
        return publisher;
    };

    private final Converter<List<Long>, List<Author>> toAuthors = (ctx) -> {
        if (ctx.getSource() == null) {
            return null;
        }
        return ctx.getSource().stream()
                .map(id -> {
                    Author author = new Author();
                    author.setId(id);
                    return author;
                })
                .toList();
    };

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT)
                .setAmbiguityIgnored(true);

        mapper.createTypeMap(CreateBookRequest.class, Book.class)
                .addMappings(m -> {
                    m.using(toGenre).map(CreateBookRequest::getGenreId, Book::setGenre);
                    m.using(toPublisher).map(CreateBookRequest::getPublisherId, Book::setPublisher);
                    m.using(toAuthors).map(CreateBookRequest::getAuthorIds, Book::setAuthors);
                });
        return mapper;
    }
}
