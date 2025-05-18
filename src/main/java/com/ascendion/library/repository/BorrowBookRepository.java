package com.ascendion.library.repository;

import com.ascendion.library.constants.BorrowStatus;
import com.ascendion.library.entity.BorrowBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowBookRepository extends JpaRepository<BorrowBook, Long> {

    List<BorrowBook> findByBookIdAndBorrowerIdAndBorrowStatus(Long bookId, Long borrowerId, BorrowStatus borrowStatus);

}
