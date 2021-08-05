package ceffx.sample;

import java.net.MalformedURLException;
import java.net.URL;

import ceffx.Cef;
import ceffx.javafx.BrowserView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CEFSampleApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		
		Cef cef = new Cef();
		cef.start();
		
		Button b = new Button("Launch Browser");
		b.setOnAction( evt -> {
			try {
				BrowserView view = new BrowserView(cef, new URL("http://www.bestsolution.at"));
				pane.setCenter(view);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		pane.setTop(b);
		
		
		
		Scene s = new Scene(pane, 800, 600);
		primaryStage.setScene(s);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
