package org.juanmariiaa.test;

import org.juanmariiaa.model.DAO.TeamDAO;
import org.juanmariiaa.model.domain.Team;

import java.sql.SQLException;
import java.util.List;

public class InsertTeam {
    public static void main(String[] args) {
// Creamos un objeto TeamDAO
        TeamDAO teamDAO = new TeamDAO();

        // Probamos el método findAll()
        try {
            List<Team> teams = teamDAO.findAll();
            System.out.println("Equipos encontrados:");
            for (Team team : teams) {
                System.out.println(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
