package org.juanmariiaa.test;

import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.connection.ConnectionMariaDB;
import org.juanmariiaa.model.domain.Team;

import java.sql.SQLException;

public class TestUpdateTeam {
    /*public static void main(String[] args) {
        // Creamos una instancia del DAO del equipo
        TeamDAO teamDAO = new TeamDAO(ConnectionMariaDB.getConnection());

        try {
            // Recuperamos el equipo con ID 3
            Team teamToUpdate = teamDAO.findById(3);

            System.out.println(teamToUpdate);

            // Verificamos si el equipo existe
            if (teamToUpdate != null) {
                // Modificamos el nombre, la ciudad y la institución del equipo
                teamToUpdate.setName("Nuevo Nombre del Equipo");
                teamToUpdate.setCity("Nueva Ciudad del Equipo");
                teamToUpdate.setInstitution("Nueva Institución del Equipo");

                // Actualizamos el equipo
                teamDAO.save(teamToUpdate);

                // Recuperamos el equipo actualizado
                Team updatedTeam = teamDAO.findById(3);

                // Mostramos el equipo actualizado
                System.out.println("Equipo actualizado:");
                System.out.println(updatedTeam);
            } else {
                System.out.println("No se encontró ningún equipo con ID 3.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
*/
}
