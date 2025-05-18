package com.ascendion.library.dto.response;

import com.ascendion.library.entity.Borrower;

public record BorrowerResponse(Long id, String name, String email) {
    public BorrowerResponse(Borrower borrower){
        this(borrower.getId(), borrower.getName(), borrower.getEmail());
    }
}
