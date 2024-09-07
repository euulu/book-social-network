package org.eulu.booknetwork.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    @Query("""
            select book
            from Book book
            where book.archived = false
            and book.shareable = true
            and book.owner != :userId
            """)
    Page<Book> findAllDisplayable(Pageable pageable, Integer userId);
}
