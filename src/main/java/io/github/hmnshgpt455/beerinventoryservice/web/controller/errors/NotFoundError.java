package io.github.hmnshgpt455.beerinventoryservice.web.controller.errors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class NotFoundError {

    private String errorCode;
    private String errorMessage;
}
