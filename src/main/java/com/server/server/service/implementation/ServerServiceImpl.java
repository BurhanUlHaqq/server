package com.server.server.service.implementation;

import com.server.server.enumaration.Status;
import com.server.server.model.Server;
import com.server.server.repo.ServerRepo;
import com.server.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
    private final ServerRepo serverRepo;

    @Override
    public Server create(Server server) {
        log.info("Saving server: " + server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server: "+ ipAddress);
        Server server =serverRepo.findByIPAddress(ipAddress);
        InetAddress address=InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)? Status.SERVER_UP:Status.SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching All Servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}",id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: " + server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server: " + id);
        serverRepo.deleteById(id);
        return true;
    }
}
