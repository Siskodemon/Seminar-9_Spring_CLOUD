package com.evilcorp;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Issue {

    private UUID id;
    private LocalDate issued;
    private UUID bookId;
    private UUID readerId;



}
