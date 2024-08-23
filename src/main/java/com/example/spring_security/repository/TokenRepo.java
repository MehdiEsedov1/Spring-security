package com.example.spring_security.repository;

import com.example.spring_security.token.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<ConfirmationToken, Long> {
    public ConfirmationToken findConfirmationTokenByToken(String email);
}
