package restaurantmanagement;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    public static Connection connectDb() {
        try {
            // Charger le driver MySQL (version 8 ou plus)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données 'restaurant' sur localhost (port 3306 par défaut)
         Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/restaurant", "root", "96348599Ra");


            // Retourne la connexion
            return connect;

        } catch (Exception e) {
            // Si une exception se produit, affiche le message d'erreur
            e.printStackTrace();
        }

        // Retourne null si la connexion échoue
        return null;
    }

}

