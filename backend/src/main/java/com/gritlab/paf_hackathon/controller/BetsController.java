package com.gritlab.paf_hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.gritlab.paf_hackathon.repository.BetsRepository;

@RestController
@RequestMapping("/bets")
public class BetsController {

    @Autowired
    private BetsRepository betsRepository;

    //bets single
    @PostMapping
    @GetMapping("/single")

    //bets cpmbinations
}
