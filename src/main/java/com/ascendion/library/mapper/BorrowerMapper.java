package com.ascendion.library.mapper;

import com.ascendion.library.dto.request.BorrowerRequest;
import com.ascendion.library.entity.Borrower;

public class BorrowerMapper {

    public static Borrower toBorrower(BorrowerRequest borrowerRequest){
        return Borrower.builder()
                .name(borrowerRequest.name())
                .email(borrowerRequest.email())
                .build();
    }
}
