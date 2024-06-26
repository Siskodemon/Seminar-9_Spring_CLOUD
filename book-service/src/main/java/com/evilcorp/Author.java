package com.evilcorp;

import lombok.Data;

import java.util.UUID;

@Data
public class Author {

    private UUID id;
    private String firstname;
    private String lastname;



}
