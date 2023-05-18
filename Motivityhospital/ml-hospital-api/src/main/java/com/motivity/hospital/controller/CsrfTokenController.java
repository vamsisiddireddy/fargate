package com.motivity.hospital.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenController {
    @GetMapping("/api/csrf")
    public CsrfToken getToken(CsrfToken token)
    {

        return token;
    }

}
