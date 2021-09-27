package ceffx.sample;

import java.net.MalformedURLException;
import java.net.URL;

import ceffx.Cef;
import ceffx.javafx.BrowserView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: black");
		
		cef = new Cef();
		cef.start();
		
		
		Scene s = new Scene(pane, 800, 600);
		primaryStage.setTitle("Java-8/FX-8 - Filament WebGL Parquet - https://google.github.io/filament/webgl/parquet.html");
		primaryStage.setScene(s);
		primaryStage.show();
		
		Timeline t = new Timeline(new KeyFrame(Duration.millis(1000), evt -> {
			setupBrowser(pane);
		}));
		t.play();
	}
	
	private void setupBrowser(BorderPane pane) {
		try {
			BrowserView view = new BrowserView(cef, new URL("https://google.github.io/filament/webgl/parquet.html"));
			pane.setCenter(view);
			
			pane.getScene().addEventFilter(KeyEvent.KEY_PRESSED, evt -> {
				if( evt.getCode() == KeyCode.DIGIT1 ) {
					sampleTransform(view);
				} else if( evt.getCode() == KeyCode.DIGIT2 ) {
					applyEffect(view);
				}
			});
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
	
	@Override
	public void stop() throws Exception {
		cef.shutdown();
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
