package com.kangkimleekojangcho.akgimi.challenge.adapter.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/postscripts")
@RestController
public class PostscriptController {

    @PostMapping
    public void createPostscript() {

    }
}
