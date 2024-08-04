package org.demo.footballresource.jpa.service;

import lombok.RequiredArgsConstructor;
import org.demo.footballresource.jpa.entity.JpaTeamEntity;
import org.demo.footballresource.jpa.model.JpaPlayer;
import org.demo.footballresource.jpa.model.JpaTeam;
import org.demo.footballresource.jpa.repository.JpaPlayerRepository;
import org.demo.footballresource.jpa.repository.JpaTeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class JpaFootballService {

    private final JpaTeamRepository jpaTeamRepository;
    private final JpaPlayerRepository jpaPlayerRepository;

    // Search players that contain the given name
    public List<JpaPlayer> searchPlayers(String name) {
        return jpaPlayerRepository.findByNameContaining(name).stream()
                .map(it -> new JpaPlayer(it.getId(), it.getName(), it.getJerseyNumber(), it.getPosition(), it.getDateOfBirth()))
                .toList();
    }

    // Search players by date of birth
    public List<JpaPlayer> searchPlayersByDateOfBirth(LocalDate dateOfBirth) {
        return jpaPlayerRepository.findByDateOfBirth(dateOfBirth).stream()
                .map(it -> new JpaPlayer(it.getId(), it.getName(), it.getJerseyNumber(), it.getPosition(), it.getDateOfBirth()))
                .toList();
    }

    // Return a team including its players
    public JpaTeam readTeam(Integer teamId) {
        var teamEntity = jpaTeamRepository.findById(teamId).orElseThrow();
        return new JpaTeam(teamEntity.getId(), teamEntity.getName(),
                teamEntity.getPlayers().stream()
                        .map(it -> new JpaPlayer(it.getId(), it.getName(), it.getJerseyNumber(), it.getPosition(), it.getDateOfBirth()))
                        .toList()
        );
    }

    // Add a new team
    public JpaTeam addTeam(String name) {
        Random random = new Random();
        int randomId = random.nextInt();
        if (randomId < 0) {
            randomId = -randomId;
        }
        JpaTeamEntity jpaTeamEntity = new JpaTeamEntity(randomId, name, List.of());
        return new JpaTeam(jpaTeamEntity.getId(), jpaTeamEntity.getName(), List.of());
    }

    // Update the position of a player
    public JpaPlayer updatePlayerPosition(Integer id, String position) {
        var playerEntity = jpaPlayerRepository.findById(id).orElseThrow();
        playerEntity.setPosition(position);
        jpaPlayerRepository.save(playerEntity);
        return new JpaPlayer(playerEntity.getId(), playerEntity.getName(), playerEntity.getJerseyNumber(), playerEntity.getPosition(), playerEntity.getDateOfBirth());
    }
}
