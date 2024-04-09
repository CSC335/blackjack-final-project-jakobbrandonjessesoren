package controller_view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BlackjackGUI extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane temp = new BorderPane();
		
		Scene scene = new Scene(temp, 800, 600);
		stage.setScene(scene);
		stage.show();
		
	}

}
