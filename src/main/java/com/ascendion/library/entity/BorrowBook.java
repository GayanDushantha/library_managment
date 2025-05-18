package com.ascendion.library.entity;

import com.ascendion.library.constants.BorrowStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BorrowBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn()
    private Book book;
    @ManyToOne
    @JoinColumn()
    private Borrower borrower;
    @Enumerated(EnumType.STRING)
    private BorrowStatus borrowStatus;
    @CreationTimestamp
    private Date borrowedDate;
    private Date returnedDate;

}
