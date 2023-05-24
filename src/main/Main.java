package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import dao.JDBC;

public class Main extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws Exception {
        try {
            stage.setTitle("scheduler");
            scene = new Scene(loadFXML("login")/*, 378, 270*/);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * The setRoot method sets the menu to a particular FXML file.
     *
     * @param fxml
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    /**
     * The loadFXML method loads the FXML file.
     *
     * @param fxml
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void main(String [] args){
        JDBC.makeConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
