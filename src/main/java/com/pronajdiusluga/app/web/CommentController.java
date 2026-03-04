package com.pronajdiusluga.app.web;

import com.pronajdiusluga.app.model.Comment;
import com.pronajdiusluga.app.model.User;
import com.pronajdiusluga.app.repository.CommentRepository;
import com.pronajdiusluga.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @PostMapping("/comment")
    public String comment(Comment comment,
                          @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow();

        comment.setUser(user);

        commentRepository.save(comment);

        return "redirect:/";
    }
}