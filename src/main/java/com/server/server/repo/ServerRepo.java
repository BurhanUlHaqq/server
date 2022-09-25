package com.server.server.repo;

import com.server.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerRepo extends JpaRepository<Server,Long> {
    Server findByIPAddress(String ipAddress);
}
