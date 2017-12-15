
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ZFMain extends Application{

	 
 	static Logger log = Logger.getLogger(ZFMain.class.getName()); 
 	public static Stage stage; 
 	public static void main(String[] args) {
 		log.info("Applikation wird gestartet.");
 		Application.launch(ZFMain.class.getName());
 		log.info("Applikation wird geschlossen");
 	}
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("view/main.fxml"));
		log.info("Scene wird initialisiert");
		AnchorPane content;
		content = (AnchorPane) loader.load();
		Scene scene = new Scene(content);
		stage.setResizable(false);
		stage.setTitle("Zeitverarbeitung");
		stage.setScene(scene);
		stage.show();
	}

}
