package ceffx.sample;

import java.net.MalformedURLException;
import java.net.URL;

import ceffx.Cef;
import ceffx.javafx.BrowserView;
import ceffx.javafx.DefaultCefMessageLoopManagerFactory;
import ceffx.process.ProcessCef;
import ceffx.util.CefMessageLoopManager;
import ceffx.util.CefMessageLoopManagerFactory;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CEFSampleApplication extends Application {
	Cef cef;
	
	private boolean transformed;
	
	private int effectCount;

	private final CefMessageLoopManagerFactory messageLoopHandlerFactory = new DefaultCefMessageLoopManagerFactory();
	
	private BrowserView view;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: black");
		
		cef = new ProcessCef();

		CefMessageLoopManager messageLoopHandler = messageLoopHandlerFactory.createMessageLoopManager(cef);
		messageLoopHandler.startMessageLoop().thenRunAsync( () -> {
			// Hack wait a bit because it looks like streams are not yet setup
			Timeline t = new Timeline(new KeyFrame(Duration.millis(10000), evt -> {
				setupBrowser(pane);
			}));
			t.play();
		}, Platform::runLater);
		
		Scene s = new Scene(pane, 800, 600);
		primaryStage.setTitle("Java-8/FX-8 - Filament WebGL Parquet - https://google.github.io/filament/webgl/parquet.html");
		primaryStage.setScene(s);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest( evt -> {
			view.close();
			try {
				messageLoopHandler.stopMessageLoop();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private void setupBrowser(BorderPane pane) {
		try {
			view = new BrowserView(cef, new URL("https://google.github.io/filament/webgl/parquet.html"));
			pane.setCenter(view);
			
			pane.getScene().addEventFilter(KeyEvent.KEY_PRESSED, evt -> {
				if( evt.getCode() == KeyCode.DIGIT1 ) {
					sampleTransform(view);
				} else if( evt.getCode() == KeyCode.DIGIT2 ) {
					applyEffect(view);
				} else if( evt.getCode() == KeyCode.DIGIT3 ) {
					sampleOpenVideoTweet(pane);
					effectCount = 0;
				}
			});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sampleOpenVideoTweet(BorderPane pane) {
		try {
			view = new BrowserView(cef, new URL("https://twitter.com/tomsontom/status/1436832853187342339"));
			pane.setCenter(view);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void sampleTransform(BrowserView view) {
		ScaleTransition scale = new ScaleTransition(Duration.millis(3000), view);
		scale.setInterpolator(Interpolator.EASE_IN);
		
		TranslateTransition translate = new TranslateTransition(Duration.millis(3000), view);
		
		if( transformed ) {
			translate.setFromY(-100);
			translate.setToY(0);
			
			scale.setFromX(0.5);
			scale.setFromY(0.5);
			
			scale.setToX(1);
			scale.setToY(1);
		} else {
			translate.setFromY(0);
			translate.setToY(-100);
			
			scale.setFromX(1);
			scale.setFromY(1);
			
			scale.setToX(0.5);
			scale.setToY(0.5);
		}
		
		transformed = ! transformed;
		
		ParallelTransition p = new ParallelTransition(scale, translate);
		p.play();
	}
	
	private void applyEffect(BrowserView view) {
		Reflection reflection = new Reflection();
		reflection.setFraction(0.75);
		reflection.setTopOffset(10);
		
		Effect effect = reflection;
		
		if( effectCount % 2 == 1 ) {
			SepiaTone sepiatone = new SepiaTone();
			sepiatone.setInput(effect);
			effect = sepiatone;
		}
		
		view.setEffect(effect);
		
		effectCount += 1;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
