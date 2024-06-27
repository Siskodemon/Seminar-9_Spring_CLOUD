package com.evilcorp.api;

import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/api/issue")
public class IssueController {

   private final List<Issue> issues;
   private final Faker faker;


    public IssueController(BookProvider bookProvider) {
        final List<Issue> issues = new ArrayList<>();
        this.faker = new Faker();

        for (int i = 0; i < 15; i++) {
            Issue issue = new Issue();

            issue.setId(UUID.randomUUID());
            issue.setIssued(faker.date().between(setStartOfYear(),setEndOfYear()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            issue.setBookId(bookProvider.getRandomBookId());
            issue.setReaderId(UUID.randomUUID());


            issues.add(issue);
        }

        this.issues = List.copyOf(issues);
    }

    private Date setStartOfYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private Date setEndOfYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, 12);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        return calendar.getTime();
    }

    @GetMapping
    public List<Issue> getALL(){
     return issues;
    };

    @GetMapping("/random")
    public Issue getRandom(){
        final int randomIndex = faker.number().numberBetween(0, issues.size());
     return issues.get(randomIndex);
    };

}
