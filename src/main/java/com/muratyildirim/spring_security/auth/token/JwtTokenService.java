package com.muratyildirim.spring_security.auth.token;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "murtimur.token-type", havingValue = "jwt")
public class JwtTokenService {

}
