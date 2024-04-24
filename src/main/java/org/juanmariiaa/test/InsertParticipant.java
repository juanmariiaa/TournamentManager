package org.juanmariiaa.test;

import org.juanmariiaa.model.DAO.ParticipantDAO;
import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Participant;
import org.juanmariiaa.model.enums.Gender;
import org.juanmariiaa.model.enums.Role;

import java.sql.SQLException;
import java.util.List;

public class InsertParticipant {
    public static void main(String[] args) {
        // Crear un objeto ParticipantDAO
        ParticipantDAO participantDAO = new ParticipantDAO();

        // Crear un nuevo participante
        Participant participant = new Participant("12345678A", "John", "Doe", 25, Role.INTRODUCTION, Gender.MALE);

        try {
            // Insertar el participante en la base de datos
            participantDAO.save(participant);
            System.out.println("Participante insertado: " + participant);

            // Buscar el participante por su DNI
            Participant retrievedParticipant = participantDAO.findById("12345678A");
            System.out.println("Participante encontrado: " + retrievedParticipant);

            // Actualizar la edad del participante
            retrievedParticipant.setAge(30);
            participantDAO.save(retrievedParticipant);
            System.out.println("Participante actualizado: " + retrievedParticipant);

            // Eliminar el participante de la base de datos
            participantDAO.delete("12345678A");
            System.out.println("Participante eliminado");

            // Mostrar todos los participantes que quedan en la base de datos
            System.out.println("Lista de participantes:");
            participantDAO.findAll().forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
