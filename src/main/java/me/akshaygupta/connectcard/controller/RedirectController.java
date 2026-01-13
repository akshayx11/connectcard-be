package me.akshaygupta.connectcard.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import me.akshaygupta.connectcard.service.ClickService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final ClickService clickService;

    @GetMapping("/r/{linkId}")
    public void redirect(@PathVariable String linkId,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String url = clickService.registerClick(linkId, request);
        response.sendRedirect(url);
    }
}
