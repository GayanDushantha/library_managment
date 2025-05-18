package com.ascendion.library.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class ValidationErrorResponse {

    private final String message;
    private final Map<String, List<String>> errors;
}
