package com.graphql.api.security.httpTypes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRefreshRequest {
    String refreshToken;
}
