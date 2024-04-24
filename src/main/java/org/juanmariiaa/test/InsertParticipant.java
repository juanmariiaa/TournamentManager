package org.juanmariiaa.test;

import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.domain.Team;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

import java.sql.SQLException;
import java.util.List;

public class InsertParticipant {
    public static void main(String[] args) throws SQLException {
    // Crear una instancia de TeamDAO
    TeamDAO teamDAO = new TeamDAO(ConnectionMariaDB.getConnection());

    // Obtener un equipo existente (supongamos que el equipo con ID 3 ya existe)
    Team team = teamDAO.findById(3);

    // Crear un nuevo participante
    Participant participant = new Participant();
    participant.setDni("12345678A");
    participant.setRole(Role.INTRODUCTION);
    participant.setGender(Gender.MALE);
    participant.setName("Juan");
    participant.setSurname("Pérez");
    participant.setAge(30);
    participant.setTeam(team); // Establecer el equipo para el participante

    // Crear una instancia de ParticipantDAO
    ParticipantDAO participantDAO = new ParticipantDAO(ConnectionMariaDB.getConnection());

    // Handle the SQLException
        // Insertar el nuevo participante en la base de datos
        boolean inserted = participantDAO.insert(participant);
        if (inserted) {
            System.out.println("Participante insertado correctamente.");
        } else {
            System.out.println("No se pudo insertar el participante.");
        }

        System.out.println(participant);
    }
}
