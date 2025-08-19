package com.eli.bims.service;

import com.eli.bims.dao.Author;
import com.eli.bims.dao.Book;
import com.eli.bims.dao.Genre;
import com.eli.bims.dto.request.SearchBookRequest;
import com.eli.bims.dto.response.BookResponse;
import com.eli.bims.repository.BookRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class BookSearchServiceImpl implements BookSearchService {

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public BookSearchServiceImpl(final ModelMapper modelMapper, final BookRepository bookRepository) {
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<BookResponse> search(final SearchBookRequest searchBookRequest, final Pageable pageable) {
        Specification<Book> spec = Specification.allOf(
                distinct(),
                titleContains(searchBookRequest.getTitle()),
                hasAuthorId(searchBookRequest.getAuthorId()),
                authorNameContains(searchBookRequest.getAuthorName()),
                hasGenreId(searchBookRequest.getGenreId()),
                genreNameContains(searchBookRequest.getGenreName())
        );

        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        List<BookResponse> bookResponses = bookPage.getContent().stream()
                .map(b -> modelMapper.map(b, BookResponse.class))
                .toList();

        return PageableExecutionUtils.getPage(bookResponses, bookPage.getPageable(), bookPage::getTotalElements);
    }

    private Specification<Book> distinct() {
        return (root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            return null;
        };
    }

    private Specification<Book> titleContains(final String title) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(title)) {
                return null;
            }

            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    private Specification<Book> hasAuthorId(final Long authorId) {
        return (root, query, cb) -> {
            if (authorId == null) {
                return null;
            }

            Join<Book, Author> authorJoin = root.join("authors", JoinType.INNER);
            return cb.equal(authorJoin.get("id"), authorId);
        };
    }

    private Specification<Book> authorNameContains(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }

            Join<Book, Author> authorJoin = root.join("authors", JoinType.INNER);
            return cb.like(cb.lower(authorJoin.get("displayName")), "%" + name.toLowerCase() + "%");
        };
    }

    private Specification<Book> hasGenreId(final Long genreId) {
        return (root, query, cb) -> {
            if (genreId == null) {
                return null;
            }

            return cb.equal(root.get("genre").get("id"), genreId);
        };
    }

    private Specification<Book> genreNameContains(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }

            Join<Book, Genre> genreJoin = root.join("genre", JoinType.INNER);
            return cb.like(cb.lower(genreJoin.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
