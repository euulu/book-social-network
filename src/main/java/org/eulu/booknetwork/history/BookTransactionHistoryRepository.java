package org.eulu.booknetwork.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {
    @Query("""
            select history
            from BookTransactionHistory history
            where history.user.id = :userId
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

    @Query("""
            select history
            from BookTransactionHistory history
            where history.book.owner.id = :userId
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

    @Query("""
            select (count(*) > 0) as isBorrowed
            from BookTransactionHistory bth
            where bth.user.id = :userId
            and bth.book.id = :bookId
            and bth.returnApproved = false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);

    @Query("""
            select bth
            from BookTransactionHistory bth
            where bth.user.id = :userId
            and bth.book.id = :bookId
            and bth.returned = false
            and bth.returnApproved = false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);
}
