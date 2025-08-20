package com.eli.bims.repository;

import com.eli.bims.dao.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Override
    @EntityGraph(attributePaths = {"genre", "publisher"})
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

    @Modifying
    @Query("update book b set b.quantity = b.quantity - :quantity where b.id = :bookId")
    void buy(@Param("bookId") Long bookId, @Param("quantity") int quantity);

    @Modifying
    @Query("update book b set b.quantity = b.quantity + :quantity where b.id = :bookId")
    void cancel(@Param("bookId") Long bookId, @Param("quantity") int quantity);
}
