/**
 * The LoginScreenController class implements the code behind the login.fxml file. It allows a User to login to the application
 * while recording their login attempts and determining their location, adjusting languange as necessary.
 *
 * @author Jacob Smith
 */

package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.ZoneId;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import dao.UserDAO;
import model.User;

public class LoginScreenController implements Initializable {
    public TextField usernameField;
    public TextField passwordField;
    public Label zoneIDLabel;
    public Label schedulerLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button loginButton;
    private Locale locale = Locale.getDefault();

    /**
     * The initialize method determines the User's location and time zone and adjusts the display accordingly, with the
     * correct language and time zone displayed.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        ZoneId zone = ZoneId.systemDefault();
        String location;
        if(locale.getLanguage().equals("fr")){
            location = zone.getDisplayName(TextStyle.FULL, Locale.FRENCH);
            zoneIDLabel.setText("Fuseau Horaire: " + location);
            schedulerLabel.setText("Programmateur");
            usernameLabel.setText("Nom D'utilisateur: ");
            passwordLabel.setText("Mot de Passe: ");
            loginButton.setText("Connectez-vous");
        }else{
            location = zone.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            zoneIDLabel.setText("Time Zone: " + location);
        }
    }

    /**
     * The loginAttempt method runs whenever a User clicks the login button. It checks the submitted username and password
     * against the database and logs the User in if the inputs are valid.
     *
     * @throws Exception
     */
    public void loginAttempt() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = UserDAO.getUser(username);
        String filename = "login_attempts.txt";
        FileWriter writer = new FileWriter(filename, true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm:ss z");
        ZonedDateTime now = ZonedDateTime.now();
        writer.write(now.format(formatter));
        writer.write("\n\tUser: " + username + "\tPassword: " + password);
        if(user == null || !(password.equals(user.getUserPassword()))){
            Alert error = new Alert(Alert.AlertType.ERROR);
            if(locale.getLanguage().equals("fr")){
                error.setHeaderText("Entrée non valide!");
                error.setContentText("Le nom d'utilisateur ou le mot de passe était incorrect.");
                error.showAndWait();
            }else{
                error.setHeaderText("Input not valid!");
                error.setContentText("Username or Password was incorrect.");
                error.showAndWait();
            }
            writer.write("\t\tUnsuccessful login.\n");
            writer.close();
        } else {
            writer.write("\t\tSuccessful login.\n");
            writer.close();
            UserHolder userHolder = UserHolder.getInstance();
            userHolder.setUser(user);
            Stage stage = (Stage)loginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
