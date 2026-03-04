package com.pronajdiusluga.app.repository;

import com.pronajdiusluga.app.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}