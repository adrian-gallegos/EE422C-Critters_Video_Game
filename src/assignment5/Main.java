/*
 * CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Adrian Gallegos
 * ag76424
 * 17360
 * Slip days used: 1
 * Spring 2022
 */

package assignment5;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import assignment5.Critter.CritterShape;
import assignment5.Critter.TestCritter;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {

	public static Stage stage; 	// the primary stage
	public static int SCREEN_WIDTH = 775;
	public static int SCREEN_HEIGHT = 775;
	
	// These static variables are used in methods involving color
	public static int rate = 0;
 	public static ArrayList<Label> labels = new ArrayList<Label>();
 	public static ArrayList<String> critterNames = new ArrayList<String>();
	
	static int Width = SCREEN_WIDTH / Params.WORLD_WIDTH;
 	static int Height = SCREEN_HEIGHT / Params.WORLD_HEIGHT;
 	public static String LIGHT_PINK = "-fx-background-color: #945d67; -fx-text-fill:white; -fx-font-size: 205%;";
 	public static String DARK_PINK = "-fx-background-color: #592b33 ; -fx-text-fill:white; -fx-font-size: 205%;";
 	public static String LIGHT_BLUE = "-fx-background-color: #6caba7; -fx-text-fill: #f7f0f1; ";
 	public static String DARK_BLUE = "-fx-background-color: #3a9c95; -fx-text-fill:#f7f0f1;";
 	public static String LIGHT_RED = "-fx-background-color: #e74c3c; -fx-text-fill: #f7f0f1; ";
 	public static String DARK_RED = "-fx-background-color: #c0392b; -fx-text-fill:#f7f0f1;";
	
 	// These static variables are used in methods involving the controller
 	static Label makeMultLabel = new Label("Multiplier");
	static Slider makeAmtSlider = new Slider(0, 100, 1);
	static Slider makeAmtMult = new Slider(0, 10, 1); 
	static Label makeAmtVal = new Label(Integer.toString((int) makeAmtSlider.getValue())); 
	static Label makeMultVal = new Label(Integer.toString((int) makeAmtMult.getValue()));
	
	// These static variables are used in methods involving animation
	public static int started;
 	static Button animationStop;
 	public static int num = 0;
 	public static int num1 = 0;
 	static GridPane world;

 	// These static variables are used in methods involving run stats
 	public static int stats_counter = 1;
 	public static GridPane statsPane;
 	public static ArrayList<Label> stats_labelList = new ArrayList<Label>();
 	public static ArrayList<String> stats_critterNameList = new ArrayList<String>();
 	public static String statsLabelStyle = "-fx-text-fill: white; -fx-font-size: 150%; ";
 	public static String statsCheckStyle = "-fx-text-fill: white; -fx-font-size: 150%; -fx-body-color: #2eccc9;"
 			+ " -fx-focus-color: white; -fx-inner-border: #27ae60; -fx-body-fill: white ";
 	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage my_stage) throws Exception {
		stage = my_stage;
		stage.setResizable(false);
		stage.setTitle("Critters, the sequel");
		GridPane screen = new GridPane();	
		
		Critter.genClover();
		Critter.displayWorld(screen);
		
	}

	 	/*
	 	 * Colors the grid as well as the grid lines
	 	 */
	 	private static void colorGridLines(GridPane grid) {
	 		for (int i = 0; i < Params.WORLD_WIDTH; i++) {
	 			for (int j = 0; j < Params.WORLD_HEIGHT; j++) {
	 				Shape s = new Rectangle(Width, Height);
	 				s.autosize();
	 				s.setFill(Color.rgb(89, 43, 51));
	 				s.setStroke(Color.rgb(0, 0, 0));
	 				grid.add(s, i, j);
	 			}
	 		}
	 	}

	 	/*
	 	 * Paints the icon shapes on a grid.
	 	 */
	 	public static void color(GridPane grid) {
	 		grid.getChildren().clear(); // clear the grid
	 		colorGridLines(grid); // paint the borders
	 		ArrayList<Critter> critters = null;
	 		try {
	 			critters = (ArrayList<Critter>) Critter.getInstances(null);
	 		} catch (InvalidCritterException e) {
	 			e.printStackTrace();
	 		}
	 		for (int i = 0; i < critters.size(); i++) {
	 			Shape s = null;
	 			if (critters.get(i).viewShape() == CritterShape.CIRCLE) {
	 				s = getShape(0);
	 			}
	 			else if (critters.get(i).viewShape() == CritterShape.SQUARE) {
	 				s = getShape(1);
	 			}
	 			else if (critters.get(i).viewShape() == CritterShape.TRIANGLE) {
	 				s = getShape(2);
	 			}
	 			else if (critters.get(i).viewShape() == CritterShape.DIAMOND) {
	 				s = getShape(3);
	 			}
	 			else if (critters.get(i).viewShape() == CritterShape.STAR) {
	 				s = getShape(4);
	 			}
	 			s.setFill(critters.get(i).viewFillColor());
	 			s.setStroke(critters.get(i).viewOutlineColor());
	 			grid.add(s, critters.get(i).getX_coord(), critters.get(i).getY_coord());
	 		}
	 	}

	 	/*
	 	 * Returns a specific shape depending on the index provided
	 	 *
	 	 */
	 	static Shape getShape(int myShape) {
	 		// create our shape based on the smaller dimension
	 		int smallerDim;
	 		if (Width < Height) {
	 			smallerDim = Width;
	 		} else
	 			smallerDim = Height;

	 		Shape s = null;
	 		if (myShape == 0) {
	 			s = new Circle(smallerDim / 2);
	 		}
	 		else if (myShape == 1) {
	 			s = new Rectangle(smallerDim, smallerDim);
	 		}
	 		else if (myShape == 2) {
	 			s = new Path(new MoveTo(0,0), new LineTo(-smallerDim+2,0), new LineTo(-smallerDim/2,-smallerDim+2), new LineTo(0,0));
	 		}
	 		else if (myShape == 3) {
	 			s = new Path(new MoveTo(0,0), new LineTo(smallerDim/2,smallerDim/2-2), new LineTo(smallerDim-2,0), new LineTo(smallerDim/2,-smallerDim/2+2), new LineTo(0,0));
	 		}
	 		else if (myShape == 4) {
	 			s = new Path(new MoveTo(0,0), new LineTo(smallerDim/3+smallerDim/25, smallerDim/3-smallerDim/6), new LineTo(smallerDim/2,smallerDim/2-2), new LineTo(3*smallerDim/4.7, smallerDim/3-smallerDim/6), new LineTo(smallerDim-3,0), new LineTo(3*smallerDim/4.7, -smallerDim/3+smallerDim/6),new LineTo(smallerDim/2,-smallerDim/2+2), new LineTo(smallerDim/3+smallerDim/25, -smallerDim/3+smallerDim/6), new LineTo(0,0));
	 		}
	 		return s;
	 	}

	 	// Called by displayWorld()
	 	public static void colorUI(GridPane ui, GridPane world) {
	 		ui.setStyle("-fx-font-family: futura;");
	 		GridPane editPane = new GridPane();
	 		editPane.setMinSize((SCREEN_WIDTH / 7) * 6, SCREEN_HEIGHT);
	 		
	 		VBox side = new VBox();
	 		side.setPrefSize(SCREEN_WIDTH / 7, SCREEN_HEIGHT);
	 		side.setStyle("-fx-background-color: #34495e");
	 		
	 		VBox edit = new VBox(editPane);
	 		edit.setPrefSize((SCREEN_WIDTH / 7) * 6, SCREEN_HEIGHT);
	 		edit.setStyle("-fx-background-color: #945d67");
	 		
	 		Button critters = new Button();
	 		critters.setText("Critters");
	 		critters.setOnAction(new EventHandler<ActionEvent>() {

	 			@Override
	 			public void handle(ActionEvent event) {
	 				InputUI(critters, editPane, world);
	 			}

	 		});

	 		HBox crittersHbox = hoverBorderBox(critters);

	 		Button animation = new Button();
	 		animation.setText("Animate");
	 		

	 		HBox animationHbox = hoverBorderBox(animation);

	 		Button stats = new Button();
	 		stats.setText("Stats");
	 		stats.setOnAction(new EventHandler<ActionEvent>() {

	 			@Override
	 			public void handle(ActionEvent event) {
	 				RunStatsUI(stats, editPane, world);
	 			}

	 		});

	 		HBox statsHbox = hoverBorderBox(stats);

	 		Button params = new Button("Params");
	 		params.setOnAction(new EventHandler<ActionEvent>() {

	 			@Override
	 			public void handle(ActionEvent event) {
	 				ParamsUI(params, editPane, world);
	 			}

	 		});

	 		HBox paramsHbox = hoverBorderBox(params);
	 		
	 		Button exit = new Button ("Exit");
	 		exit.setMinSize(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
	 		exit.setStyle(LIGHT_RED);
	 		exit.setOnMouseEntered(value->{
	 			exit.setStyle(DARK_RED);
	 		});
	 		exit.setOnMouseExited(value->{
	 			exit.setStyle(LIGHT_RED);
	 		});
	 		
	 		exit.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				System.exit(0);
	 			}	
	 		});

	 		HBox exitHbox = new HBox(exit);

	 		side.getChildren().add(paramsHbox);
	 		side.getChildren().add(statsHbox);
	 		side.getChildren().add(crittersHbox);
	 		side.getChildren().add(animationHbox);
	 		side.getChildren().add(exitHbox);
	 		
	 		ui.add(edit, 0, 0);
	 		ui.add(side, 1, 0, (SCREEN_WIDTH/5) * 4, SCREEN_HEIGHT);
	 		
	 		animation.setOnAction(new EventHandler<ActionEvent>() {

	 			@Override
	 			public void handle(ActionEvent event) {
	 		 		
	 		 		if (started == 1) {
	 		 			AnimationUI(animation, editPane, world, critters, animation, stats, params, rate);
	 		 		}
	 		 		else {
	 		 			AnimationUI(animation, editPane, world, critters, animation, stats, params);
	 		 		}
	 			}
	 		});
	 		
	 	}

	 	public static HBox hoverBorderBox(Button btn) {
	 		Pane sidePane = new Pane();
	 		sidePane.setMinSize(SCREEN_WIDTH/60, SCREEN_HEIGHT/4);

	 		HBox horizontal = new HBox(btn);

	 		horizontal.setMinSize(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 4.5);
	 		btn.setStyle("-fx-background-color: #592b33 ; -fx-text-fill:white; -fx-font-size: 205%;");
	 		btn.setMinSize((SCREEN_WIDTH / 5), SCREEN_HEIGHT / 4.2);
	 		btn.setOnMouseClicked(value -> {
	 			btn.setStyle("-fx-background-color: #945d67 ; -fx-text-fill:white; -fx-font-size: 205%;");
	 		});
	 		btn.setOnMouseEntered(value -> {
	 			btn.setStyle("-fx-background-color: #945d67 ; -fx-text-fill:white; -fx-font-size: 205%;");
	 		});
	 		btn.setOnMouseExited(value -> {
	 			btn.setStyle("-fx-background-color: #592b33 ; -fx-text-fill:white; -fx-font-size: 205%;");
	 		});
	 		
	 		return horizontal;
	 	}
	 	
	 	
		// Method that handles any button press that initiates the user-interface
	 	public static void InputUI(Button btn, GridPane editPane, GridPane world) {
	 		editPane.getChildren().clear();
	 		editPane.setOnMouseEntered(value -> {
	 			btn.setStyle(LIGHT_PINK);
	 		});
	 		editPane.setOnMouseExited(value -> {
	 			btn.setStyle(DARK_PINK);
	 		});
	 		GridPane timeStepPane = new GridPane(); // Holds all elements related to making a time step
	 		GridPane setSeedPane = new GridPane(); // Holds all elements related to setting seed

	 		// Space Panes are created in order to create spacing
	 		Pane spacePane1 = new Pane();
	 		spacePane1.setMinSize(SCREEN_WIDTH / 16, SCREEN_HEIGHT / 2);

	 		Pane spacePane2 = new Pane();
	 		spacePane2.setMinSize(SCREEN_WIDTH / 16, SCREEN_HEIGHT / 2);

	 		// Creating nodes needed for UI
	 		Label timeStepLabel = new Label("Time Step(s)");
	 		timeStepLabel.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		timeStepLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");

	 		Label setSeedLabel = new Label("Set Seed");
	 		setSeedLabel.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		setSeedLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");

	 		TextField timeStepInput = new TextField();
	 		timeStepInput.setMinSize((SCREEN_WIDTH / 5) * 1.5, (SCREEN_HEIGHT / 15));
	 		timeStepInput.setPromptText("Enter time step(s)");

	 		TextField setSeedInput = new TextField();
	 		setSeedInput.setMinSize((SCREEN_WIDTH / 5) * 1.5, (SCREEN_HEIGHT / 15));
	 		setSeedInput.setPromptText("Enter Seed(s)");

	 		// Button to set seed
	 		Button executeSeed = new Button("Set seed");
	 		executeSeed.setMinHeight(SCREEN_HEIGHT / 22);
	 		executeSeed.setStyle(LIGHT_BLUE);
	 		// change color on hover
	 		executeSeed.setOnMouseEntered(value -> {
	 			executeSeed.setStyle(DARK_BLUE);
	 		});
	 		executeSeed.setOnMouseExited(value -> {
	 			executeSeed.setStyle(LIGHT_BLUE);
	 		});
	 		// behavior on click
	 		executeSeed.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				int num = Integer.parseInt(setSeedInput.getText());
	 				Critter.setSeed(num);
	 				setSeedInput.clear();
	 			}
	 		});

	 		// Button to do time step
	 		Button executeTime = new Button("Time step(s)");
	 		executeTime.setMinHeight(SCREEN_HEIGHT / 22);
	 		executeTime.setStyle(LIGHT_BLUE);
	 		// change color on hover
	 		executeTime.setOnMouseEntered(value -> {
	 			executeTime.setStyle(DARK_BLUE);
	 		});
	 		executeTime.setOnMouseExited(value -> {
	 			executeTime.setStyle(LIGHT_BLUE);
	 		});
	 		// behavior on click
	 		executeTime.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				int num = Integer.parseInt(timeStepInput.getText());
	 				for (int i = 0; i < num; i++) {
	 					Critter.worldTimeStep();
	 				}
	 				timeStepInput.clear();
	 				color(world); // update world
	 			}
	 		});

	 		// Add all nodes to time step pane - creates the area we see for time step
	 		timeStepPane.add(timeStepLabel, 0, 0);
	 		timeStepPane.add(timeStepInput, 0, 1);
	 		timeStepPane.add(executeTime, 0, 2);

	 		// Add all nodes to set seed pane - creates the area we see for setting seeds
	 		setSeedPane.add(setSeedLabel, 0, 0);
	 		setSeedPane.add(setSeedInput, 0, 1);
	 		setSeedPane.add(executeSeed, 0, 2);

	 		// set alignments so that everything looks nice in time step
	 		GridPane.setHalignment(timeStepPane.getChildren().get(0), HPos.CENTER);
	 		GridPane.setHalignment(timeStepPane.getChildren().get(2), HPos.CENTER);
	 		GridPane.setValignment(timeStepPane.getChildren().get(2), VPos.BASELINE);

	 		// set alignments so that everything looks nice in time step
	 		GridPane.setHalignment(setSeedPane.getChildren().get(0), HPos.CENTER);
	 		GridPane.setHalignment(setSeedPane.getChildren().get(2), HPos.CENTER);
	 		GridPane.setValignment(setSeedPane.getChildren().get(2), VPos.BASELINE);

	 		timeStepPane.setVgap(SCREEN_HEIGHT / 60);
	 		setSeedPane.setVgap(SCREEN_HEIGHT / 60);

	 		timeStepPane.setAlignment(Pos.CENTER_RIGHT);
	 		setSeedPane.setAlignment(Pos.CENTER_RIGHT);

	 		// create the HBox and fill it with space pane and necessary panes
	 		HBox topBox = new HBox(spacePane1, setSeedPane, spacePane2, timeStepPane);
	 		topBox.setMinSize(SCREEN_WIDTH * (4 / 5), SCREEN_HEIGHT / 2);

	 		// creating the Make Critter area (follows similar processes as mentioned above)
	 		GridPane makeCritterPane = new GridPane();
	 		GridPane makeCritterPaneInner = new GridPane();

	 		makeCritterPaneInner.setHgap(5);
	 		Pane spacePane3 = new Pane();
	 		spacePane3.setMinSize(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 2);
	 		Pane spacePane4 = new Pane();
	 		spacePane4.setMinSize(SCREEN_WIDTH / 5, SCREEN_HEIGHT / 2);

	 		Label makeCritterLabel = new Label("Create Critter(s)");
	 		makeCritterLabel.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		makeCritterLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");
	 		
	 		Label multiplierLabel = new Label("Critter Multiplier");
	 		multiplierLabel.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		multiplierLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");

	 		// creates a drop down menu that has all the Critters in the package
	 		ComboBox<String> critterDropDown = new ComboBox<String>();
	 		List<Class<?>> classes = getallclasses();
	 		for (int i = 0; i < classes.size(); i++) {
	 			if ((!classes.get(i).getSimpleName().equals("TestCritter") && classes.get(i).getSuperclass().getName().equals(Critter.class.getName())
	 					|| classes.get(i).getSuperclass().getName().equals(TestCritter.class.getName())) == true) {
	 				critterDropDown.getItems().add(classes.get(i).getSimpleName()); // add names of critters to drop down menu
	 			}
	 		}
	 		// Default option as well as correct sizes
	 		critterDropDown.setEditable(false);
	 		critterDropDown.setValue(critterDropDown.getItems().get(0));
	 		critterDropDown.setMinWidth((SCREEN_HEIGHT / 8));
	 		critterDropDown.setMinHeight((SCREEN_HEIGHT / 16));

	 		Button executeCritter = new Button("Create Critter(s)");
	 		executeCritter.setMinHeight(SCREEN_HEIGHT / 22);
	 		executeCritter.setStyle(LIGHT_BLUE);
	 		executeCritter.setOnMouseEntered(value -> {
	 			executeCritter.setStyle(DARK_BLUE);
	 		});
	 		executeCritter.setOnMouseExited(value -> {
	 			executeCritter.setStyle(LIGHT_BLUE);
	 		});
	 		
	 		makeAmtSlider.setShowTickMarks(true);
			makeAmtSlider.setShowTickLabels(true);
			makeAmtSlider.setMajorTickUnit(25);
			makeAmtSlider.setMinorTickCount(4);
			makeAmtSlider.setPrefWidth(1000);
			makeAmtSlider.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
	            	makeAmtSlider.setValue(new_val.intValue());
	            	if (((int) makeAmtMult.getValue() * new_val.intValue() < 1) == false) { executeCritter.setDisable(false); }
	            	else { executeCritter.setDisable(true); }
	                makeAmtVal.setText(String.valueOf((int)((int) makeAmtMult.getValue() * new_val.intValue()))); }
			});
			
			makeAmtVal.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		makeAmtVal.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");
	 		
	 		/* Make amount multiplier setup */
			makeAmtMult.setShowTickMarks(true);
			makeAmtMult.setShowTickLabels(true);
			makeAmtMult.setSnapToTicks(true);
			makeAmtMult.setMajorTickUnit(5);
			makeAmtMult.setMinorTickCount(4);
			makeAmtMult.setPrefWidth(1000);
			makeAmtMult.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
	            	if (((int) makeAmtSlider.getValue() * new_val.intValue() < 1) == false) { executeCritter.setDisable(false); }
	            	else { executeCritter.setDisable(true); }
	                makeAmtVal.setText(String.valueOf((int)((int) makeAmtSlider.getValue() * new_val.intValue()))); }
	        });
	 		
	 		
	 		// sets behavior on Button click
	 		executeCritter.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				int num = (Integer.parseInt(makeAmtVal.getText()) * Integer.parseInt(makeMultVal.getText()));
	 				for (int i = 0; i < num; i++) {
	 					try {
	 						Critter.createCritter(critterDropDown.getValue());
	 					} catch (InvalidCritterException e) {

	 					}
	 				}
	 				makeAmtSlider.adjustValue(1);
	 				makeAmtMult.adjustValue(1);
	 				color(world);
	 			}
	 		});
	 		// Add Make critter stuff to bottom half HBox
	 		HBox bottomBox = new HBox(makeCritterPane);
	 		bottomBox.setMinWidth(SCREEN_WIDTH * 4 / 5);
	 		bottomBox.setMinHeight(SCREEN_HEIGHT / 2.25);
	 		bottomBox.setAlignment(Pos.CENTER);
	 		makeCritterPane.add(makeCritterLabel, 0, 0);
	 		makeCritterPaneInner.add(makeAmtSlider, 0, 0);
	 		
	 		makeCritterPaneInner.add(makeAmtVal, 1, 1);
	 		makeCritterPane.add(multiplierLabel, 0, 2);
	 		makeCritterPane.add(makeAmtMult, 0, 3);
	 		
	 		makeCritterPaneInner.add(critterDropDown, 1, 0);
	 		makeCritterPane.add(makeCritterPaneInner, 0, 1);
	 		makeCritterPaneInner.add(executeCritter, 0, 1);

	 		// Align nodes to make everything look nice
	 		GridPane.setHalignment(makeCritterPane.getChildren().get(0), HPos.CENTER);
	 		GridPane.setHalignment(makeCritterPane.getChildren().get(2), HPos.CENTER);

	 		makeCritterPane.setVgap(SCREEN_HEIGHT / 60);
	 		makeCritterPane.setHgap(SCREEN_WIDTH / 60);

	 		editPane.add(bottomBox, 0, 0);
	 		editPane.add(topBox, 0, 1);
	 	}

	 	// Method to get classes of critters
	 	static List<Class<?>> getallclasses() {
	 		String file_path = "assignment5";
	 		List<Class<?>> classes = new ArrayList<>();
	 		String[] classPathEntries = System.getProperty("java.class.path").split(System.getProperty("path.separator"));
	 		String name;
	 		for (int i = 0; i < classPathEntries.length; i++) {
	 			if (!(classPathEntries[i].endsWith(".jar"))) {
	 				try {
	 					File base = new File(classPathEntries[i] + File.separatorChar + file_path);
	 					for (int j = 0; j < base.listFiles().length; j++) {
	 					//for (File file : base.listFiles()) {
	 						name = base.listFiles()[j].getName();
	 						if (name.endsWith(".class") != false) {
	 							name = name.substring(0, name.length() - 6);
	 							classes.add(Class.forName(file_path + "." + name));
	 						}
	 					}
	 				} catch (Exception e) {}
	 			} else {
	 				File jar = new File(classPathEntries[i]);
	 				try {
	 					JarInputStream input_stream = new JarInputStream(new FileInputStream(jar));
	 					JarEntry entry = input_stream.getNextJarEntry();
	 					while (entry != null) {
	 						name = entry.getName();
	 						if (name.endsWith(".class") != false) {
	 							if ((name.contains(file_path) && name.endsWith(".class")) == true) {
	 								String classPath = name.substring(0, entry.getName().length() - 6);
	 								classPath = classPath.replaceAll("[\\|/]", ".");
	 								classes.add(Class.forName(classPath));
	 							}
	 						}
	 						entry = input_stream.getNextJarEntry();
	 					}
	 					input_stream.close();
	 				} catch (Exception e) {}
	 			}
	 		}
	 		return classes;
	 	}
	 	
	 	// Method when there's no animation
	 	public static void AnimationUI(Button btn, GridPane editPane, GridPane world, Button critters, Button animate, Button stats,
	 			Button params) {

	 		Main.world = world; // pass reference to world grid pane so that we can update it
	 		editPane.getChildren().clear();
	 		editPane.setOnMouseEntered(value -> {
	 			btn.setStyle(LIGHT_PINK);
	 		});
	 		editPane.setOnMouseExited(value -> {
	 			btn.setStyle(DARK_PINK);
	 		});
	 		Pane spacePane1 = new Pane();
	 		spacePane1.setMinSize(SCREEN_WIDTH / 16, SCREEN_HEIGHT / 2);

	 		GridPane animatePane = new GridPane();
	 		GridPane buttonPane = new GridPane();

	 		Label animateLabel = new Label("Animation");
	 		animateLabel.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		animateLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");

	 		TextField animateInput = new TextField();
	 		animateInput.setMinSize((SCREEN_WIDTH / 5) * 2.5, (SCREEN_HEIGHT / 15));
	 		animateInput.setPromptText("Time steps per frame");
	 		animateInput.setAlignment(Pos.CENTER_RIGHT);

	 		Label frameSpeed = new Label("Animation Speed");
	 		frameSpeed.setStyle("-fx-text-fill: #f7f0f1; -fx-font-family: Arial; ");

	 		Button startAnimate = new Button("Start Animation");
	 		startAnimate.setMinWidth(SCREEN_WIDTH / 10);
	 		startAnimate.setMinHeight(SCREEN_HEIGHT / 22);
	 		startAnimate.setStyle(LIGHT_BLUE);
	 		startAnimate.setOnMouseEntered(value -> {
	 			startAnimate.setStyle(DARK_BLUE);
	 		});
	 		startAnimate.setOnMouseExited(value -> {
	 			startAnimate.setStyle(LIGHT_BLUE);
	 		});
	 		startAnimate.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {

	 				num = Integer.parseInt(animateInput.getText());
	 				rate = num;
	 				started = 1;
	 				AnimationTimer timer = new MyTimer(num);
	 				startAnimate.setDisable(true);
	 				animationStop.setDisable(false);
	 				timer.start();

	 				critters.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
	 					@Override
	 					public void handle(ActionEvent event) {
	 						Alert alert = new Alert(AlertType.INFORMATION);
	 						alert.setTitle("ANIMATION ERROR");
	 						alert.setHeaderText("Error: Animation still running");
	 						alert.setContentText("You cannot access this pane without stopping the animation first. "
	 								+ "Please stop the animation, then continue.");

	 						alert.show();
	 					}
	 				});
	 				params.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
	 					@Override
	 					public void handle(ActionEvent event) {
	 						Alert alert = new Alert(AlertType.INFORMATION);
	 						alert.setTitle("ANIMATION ERROR");
	 						alert.setHeaderText("Error: Animation still running");
	 						alert.setContentText("You cannot access this pane without stopping the animation first. "
	 								+ "Please stop the animation, then continue.");

	 						alert.show();
	 					}
	 				});

	 			}
	 		});
	 		animationStop = new Button("Pause");
	 		animationStop.setDisable(true);
	 		animationStop.setMinWidth(SCREEN_WIDTH / 10);
	 		animationStop.setMinHeight(SCREEN_HEIGHT / 22);
	 		animationStop.setStyle(LIGHT_BLUE);
	 		animationStop.setOnMouseEntered(value -> {
	 			animationStop.setStyle(DARK_BLUE);
	 		});
	 		animationStop.setOnMouseExited(value -> {
	 			animationStop.setStyle(LIGHT_BLUE);
	 		});
	 		animationStop.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				started = 0;
	 				startAnimate.setDisable(false);
	 				animationStop.setDisable(true);
	 				critters.setOnAction(new EventHandler<ActionEvent>() {

	 					@Override
	 					public void handle(ActionEvent event) { // resume normal functionality
	 						InputUI(critters, editPane, world);
	 					}

	 				});
	 				params.setOnAction(new EventHandler<ActionEvent>() { // resume normal functionality

	 					@Override
	 					public void handle(ActionEvent event) {
	 						ParamsUI(params, editPane, world);
	 					}

	 				});
	 			}
	 		});
	 		animatePane.add(animateLabel, 0, 0);
	 		animatePane.add(animateInput, 0, 1);
	 		animatePane.add(frameSpeed, 1, 1);
	 		buttonPane.add(startAnimate, 0, 0);
	 		buttonPane.setHgap(5);
	 		buttonPane.add(animationStop, 1, 0);
	 		buttonPane.setAlignment(Pos.CENTER_RIGHT);

	 		animatePane.add(buttonPane, 0, 2);
	 		animatePane.setHgap(25);
	 		animatePane.setVgap(10);

	 		HBox topBox = new HBox(spacePane1, animatePane);

	 		editPane.add(topBox, 0, 0);

	 		// If there is an animation already running
	 		if (started == 1) {
	 			System.out.println(num);
	 			startAnimate.setDisable(true);
	 			animationStop.setDisable(false);
	 		}
	 	}

	 	// Method when the animation is running
	 	public static void AnimationUI(Button btn, GridPane editPane, GridPane world, Button critters, Button animate, Button stats,
	 			Button params, int num) {
	 		Main.world = world; // pass reference to world grid pane so that we can update it
	 		editPane.getChildren().clear();
	 		editPane.setOnMouseEntered(value -> {
	 			btn.setStyle(LIGHT_PINK);
	 		});
	 		editPane.setOnMouseExited(value -> {
	 			btn.setStyle(DARK_PINK);
	 		});
	 		Pane spacePane1 = new Pane();
	 		spacePane1.setMinSize(SCREEN_WIDTH / 16, SCREEN_HEIGHT / 2);

	 		GridPane animatePane = new GridPane();
	 		GridPane buttonPane = new GridPane();

	 		Label animateLabel = new Label("Animation");
	 		animateLabel.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		animateLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");

	 		TextField animateInput = new TextField();
	 		animateInput.setMinSize((SCREEN_WIDTH / 5) * 2.5, (SCREEN_HEIGHT / 15));
	 		animateInput.setText(Integer.toString(num));
	 		animateInput.setAlignment(Pos.CENTER_RIGHT);

	 		Label frameSpeed = new Label("Animation Speed");
	 		frameSpeed.setStyle("-fx-text-fill: #f7f0f1; -fx-font-family: Arial; ");

	 		Button startAnimate = new Button("Start Animation");
	 		startAnimate.setMinWidth(SCREEN_WIDTH / 10);
	 		startAnimate.setMinHeight(SCREEN_HEIGHT / 22);
	 		startAnimate.setStyle(LIGHT_BLUE);
	 		startAnimate.setOnMouseEntered(value -> {
	 			startAnimate.setStyle(DARK_BLUE);
	 		});
	 		startAnimate.setOnMouseExited(value -> {
	 			startAnimate.setStyle(LIGHT_BLUE);
	 		});

	 		startAnimate.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				num1 = Integer.parseInt(animateInput.getText());
	 				rate = num1;
	 				started = 1;
	 				AnimationTimer timer = new MyTimer(num1);
	 				startAnimate.setDisable(true);
	 				animationStop.setDisable(false);
	 				timer.start();

	 				critters.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
	 					@Override
	 					public void handle(ActionEvent event) {
	 						Alert alert = new Alert(AlertType.INFORMATION);
	 						alert.setTitle("ANIMATION ERROR");
	 						alert.setHeaderText("Error: Animation still running");
	 						alert.setContentText("You cannot access this pane without stopping the animation first. "
	 								+ "Please stop the animation, then continue.");

	 						alert.show();
	 					}
	 				});
	 				params.setOnAction(new EventHandler<ActionEvent>() { // if they try and click somewhere they can't
	 					@Override
	 					public void handle(ActionEvent event) {
	 						Alert alert = new Alert(AlertType.INFORMATION);
	 						alert.setTitle("ANIMATION ERROR");
	 						alert.setHeaderText("Error: Animation still running");
	 						alert.setContentText("You cannot access this pane without stopping the animation first. "
	 								+ "Please stop the animation, then continue.");

	 						alert.show();
	 					}
	 				});
	 			}
	 		});

	 		animationStop = new Button("Pause");
	 		animationStop.setDisable(true);
	 		animationStop.setMinWidth(SCREEN_WIDTH / 10);
	 		animationStop.setMinHeight(SCREEN_HEIGHT / 22);
	 		animationStop.setStyle(LIGHT_BLUE);
	 		animationStop.setOnMouseEntered(value -> {
	 			animationStop.setStyle(DARK_BLUE);
	 		});
	 		animationStop.setOnMouseExited(value -> {
	 			animationStop.setStyle(LIGHT_BLUE);
	 		});
	 		animationStop.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				started = 0;
	 				startAnimate.setDisable(false);
	 				animationStop.setDisable(true);
	 				critters.setOnAction(new EventHandler<ActionEvent>() { // set normal functionality

	 					@Override
	 					public void handle(ActionEvent event) {
	 						InputUI(critters, editPane, world);
	 					}

	 				});
	 				params.setOnAction(new EventHandler<ActionEvent>() { // set normal functionality

	 					@Override
	 					public void handle(ActionEvent event) {
	 						ParamsUI(params, editPane, world);
	 					}

	 				});
	 			}
	 		});
	 		animatePane.add(animateLabel, 0, 0);
	 		animatePane.add(animateInput, 0, 1);

	 		animatePane.add(frameSpeed, 1, 1);

	 		buttonPane.add(startAnimate, 0, 0);
	 		buttonPane.setHgap(5);
	 		buttonPane.add(animationStop, 1, 0);
	 		buttonPane.setAlignment(Pos.CENTER_RIGHT);
	 		animatePane.add(buttonPane, 0, 2);
	 		animatePane.setHgap(25);
	 		HBox topBox = new HBox(spacePane1, animatePane);

	 		editPane.add(topBox, 0, 0);

	 		// If there's an animation already running
	 		if (started == 1) {
	 			System.out.println(num);
	 			startAnimate.setDisable(true);
	 			animationStop.setDisable(false);
	 		}
	 	}

	 	// Customized animation timer
	 	public static class MyTimer extends AnimationTimer {

	 		int num;
	 		int currentTimer;

	 		public MyTimer(int n) {
	 			num = n;
	 			currentTimer = 0;
	 		}

	 		@Override
	 		public void handle(long now) {
	 			if (started == 0) {
	 				stop();
	 			}
	 			currentTimer++;
	 			if (currentTimer > 15) {
	 				doHandle();
	 				currentTimer = 0;
	 			}
	 		}

	 		private void doHandle() {
	 			color(world);
	 			for (int i = 0; i < num; i++)
	 				Critter.worldTimeStep();
	 			updateStats();
	 		}
	 	}

	 	// Method that creates the run stats pane
	 	public static void RunStatsUI(Button btn, GridPane editPane, GridPane world) {
	 		stats_counter = 1; // solves issue of stats slowly moving down the page
	 		editPane.getChildren().clear();
	 		editPane.setOnMouseEntered(value -> {
	 			btn.setStyle(LIGHT_PINK);
	 		});
	 		editPane.setOnMouseExited(value -> {
	 			btn.setStyle(DARK_PINK);
	 		});
	 		// Spacing panes
	 		Pane spacePane1 = new Pane();
	 		spacePane1.setMinSize(SCREEN_WIDTH / 20, SCREEN_HEIGHT / 2);

	 		// Pane where information will be displayed
	 		GridPane statsPane = new GridPane();
	 		Main.statsPane = statsPane;
	 		statsPane.setHgap(10);

	 		Label statsLabel = new Label("Run Stats");
	 		statsLabel.setMinSize(((SCREEN_WIDTH * (4 / 5)) / 2), (SCREEN_HEIGHT / 10));
	 		statsLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");

	 		statsPane.add(statsLabel, 0, 0);
	 		statsPane.setVgap(30);
	 		
	 		List<Class<?>> classes = getallclasses(); // find all critters
	 		List<Critter> critterList = null;
	 		for (int i = 0; i < classes.size(); i++) {
	 			if (!classes.get(i).getName().equals("assignment5.Critter$TestCritter")
	 					&& classes.get(i).getSuperclass().getName().equals(Critter.class.getName())
	 					|| classes.get(i).getSuperclass().getName().equals(TestCritter.class.getName())) {
	 				CheckBox critter = new CheckBox();
	 				critterNames.add(classes.get(i).getSimpleName()); // add critter name to list
	 				critter.setText(classes.get(i).getSimpleName()); // set Checkbox text
	 				critter.setStyle(statsLabelStyle);
	 				try {
	 					critterList = Critter.getInstances(classes.get(i).getSimpleName());
	 				} catch (InvalidCritterException e) {
	 					e.printStackTrace();
	 				}
	 				Label critterStats = new Label(); // create Label to hold String stats
	 				if (!(classes.get(i).getSimpleName().equals("Goblin"))) {
	 					critterStats.setText(Critter.runStats(critterList));
	 				} else if (classes.get(i).getSimpleName().equals("Goblin")){
	 					critterStats.setText(Goblin.runStats(critterList));
	 					System.out.println(Goblin.runStats(critterList));
	 				}
	 				critterStats.setVisible(false);
	 				critterStats.setStyle(statsLabelStyle);
	 				labels.add(critterStats);
	 				statsPane.add(critter, 0, stats_counter);
	 				statsPane.add(critterStats, 1, stats_counter);
	 				stats_counter++; // increment row index so the next nodes are one row lower than the previous
	 				critter.setOnAction(new EventHandler<ActionEvent>() {
	 					@Override
	 					public void handle(ActionEvent event) {
	 						// check if checkmark is clicked or not
	 						if (critter.isSelected()) { // if clicked - show label
	 							critter.setStyle(statsCheckStyle);
	 							critterStats.setVisible(true);
	 						} else { // don't show label
	 							critterStats.setVisible(false);
	 							critter.setStyle(statsLabelStyle);
	 						}
	 					}
	 				});
	 			}
	 		}
	 		// add everything to the view
	 		HBox topBox = new HBox(spacePane1, statsPane);
	 		editPane.add(topBox, 0, 0);
	 		updateStats();
	 	}

	 	// Method that keeps updating the stats by changing the text of labels
	 	public static void updateStats() {
	 		List<Critter> critterList = null;
	 		for (int i = 0; i < labels.size(); i++) {
	 			try {
	 				critterList = Critter.getInstances(critterNames.get(i));
	 			} catch (InvalidCritterException e) {
	 				e.printStackTrace();
	 			}
	 			if (critterList.size() > 0 && critterList.get(0).getClass().getSimpleName().equals("Goblin")) {
	 				labels.get(i).setText(Goblin.runStats(critterList));
	 			} else
	 				labels.get(i).setText(Critter.runStats(critterList));
	 		}
	 	}
	 	
	 	// Method that creates the Parameter Pane
	 	public static void ParamsUI(Button btn, GridPane editPane, GridPane world) {
	 		String costLabelStyle = "-fx-text-fill: #f7f0f1; -fx-font-size: 125%; -fx-font-family: Arial; ";
	 		
	 		editPane.getChildren().clear();
	 		editPane.setOnMouseEntered(value -> {
	 			btn.setStyle(LIGHT_PINK);
	 		});
	 		editPane.setOnMouseExited(value -> {
	 			btn.setStyle(DARK_PINK);
	 		});
	 		//space panes
	 		Pane spacePane1 = new Pane();
	 		spacePane1.setMinSize(SCREEN_WIDTH/20, SCREEN_HEIGHT/2);
	 		Pane spacePane2 = new Pane();
	 		spacePane2.setMinSize(SCREEN_WIDTH/60, SCREEN_HEIGHT/2);
	 		
	 		GridPane paramsPane = new GridPane();
	 		paramsPane.setHgap(10);
	 		
	 		Label paramsLabel = new Label("Params");
	 		paramsLabel.setStyle("-fx-text-fill: #f7f0f1; -fx-font-size: 190%; -fx-font-family: Arial; ");
	 		
	 		Label walkCostLabel = new Label();
	 		walkCostLabel.setText("Walk Energy Cost:");
	 		walkCostLabel.setStyle(costLabelStyle);
	 		walkCostLabel.setWrapText(true);
	 		
	 		TextField walkCostInput = new TextField();
	 		walkCostInput.setPromptText(Integer.toString(Params.WALK_ENERGY_COST));
	 		walkCostInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button changeWalkCost = new Button();
	 		changeWalkCost.setText("Update");
	 		changeWalkCost.setMinHeight(SCREEN_HEIGHT/22);
	 		changeWalkCost.setMinWidth(SCREEN_WIDTH/8);
	 		changeWalkCost.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.WALK_ENERGY_COST = Integer.parseInt(walkCostInput.getText());
	 				walkCostInput.clear();
	 				walkCostInput.setPromptText(Integer.toString(Params.WALK_ENERGY_COST));
	 			}

	 		});
	 		changeWalkCost.setStyle(LIGHT_BLUE);
	 		changeWalkCost.setOnMouseEntered(value->{
	 			changeWalkCost.setStyle(DARK_BLUE);
	 		});
	 		changeWalkCost.setOnMouseExited(value->{
	 			changeWalkCost.setStyle(LIGHT_BLUE);
	 		});
	 		Label runCostLabel = new Label();
	 		runCostLabel.setText("Run Energy Cost:");
	 		runCostLabel.setStyle(costLabelStyle);
	 		runCostLabel.setWrapText(true);
	 		
	 		TextField runCostInput = new TextField();
	 		runCostInput.setPromptText(Integer.toString(Params.RUN_ENERGY_COST));
	 		runCostInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button changeRunCost = new Button();
	 		changeRunCost.setText("Update");
	 		changeRunCost.setMinHeight(SCREEN_HEIGHT/22);
	 		changeRunCost.setStyle(LIGHT_BLUE);
	 		changeRunCost.setOnMouseEntered(value->{
	 			changeRunCost.setStyle(DARK_BLUE);
	 		});
	 		changeRunCost.setOnMouseExited(value->{
	 			changeRunCost.setStyle(LIGHT_BLUE);
	 		});
	 		changeRunCost.setOnAction(new EventHandler<ActionEvent>() {

	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.RUN_ENERGY_COST = Integer.parseInt(runCostInput.getText());
	 				runCostInput.clear();
	 				runCostInput.setPromptText(Integer.toString(Params.RUN_ENERGY_COST));
	 			}

	 		});
	 		Label restCostLabel = new Label();
	 		restCostLabel.setText("Rest Energy Cost:");
	 		restCostLabel.setStyle(costLabelStyle);
	 		restCostLabel.setWrapText(true);
	 		
	 		TextField restCostInput = new TextField();
	 		restCostInput.setPromptText(Integer.toString(Params.REST_ENERGY_COST));
	 		restCostInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button changeRestCost = new Button("Update");
	 		changeRestCost.setMinHeight(SCREEN_HEIGHT/22);
	 		changeRestCost.setStyle(LIGHT_BLUE);
	 		changeRestCost.setOnMouseEntered(value->{
	 			changeRestCost.setStyle(DARK_BLUE);
	 		});
	 		changeRestCost.setOnMouseExited(value->{
	 			changeRestCost.setStyle(LIGHT_BLUE);
	 		});
	 		changeRestCost.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.REST_ENERGY_COST = Integer.parseInt(restCostInput.getText());
	 				restCostInput.clear();
	 				restCostInput.setPromptText(Integer.toString(Params.REST_ENERGY_COST));
	 			}
	 		});
	 		
	 		Label reproduceLabel = new Label("Minimum Reproduction Energy: ");
	 		reproduceLabel.setWrapText(true);
	 		reproduceLabel.setStyle(costLabelStyle);
	 		
	 		TextField reproduceInput = new TextField();
	 		reproduceInput.setPromptText(Integer.toString(Params.MIN_REPRODUCE_ENERGY));
	 		reproduceInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button changeReproduce = new Button();
	 		changeReproduce.setText("Update");
	 		changeReproduce.setMinHeight(SCREEN_HEIGHT/22);
	 		changeReproduce.setStyle(LIGHT_BLUE);
	 		changeReproduce.setOnMouseEntered(value->{
	 			changeReproduce.setStyle(DARK_BLUE);
	 		});
	 		changeReproduce.setOnMouseExited(value->{
	 			changeReproduce.setStyle(LIGHT_BLUE);
	 		});
	 		changeReproduce.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.MIN_REPRODUCE_ENERGY = Integer.parseInt(reproduceInput.getText());
	 				reproduceInput.clear();
	 				reproduceInput.setPromptText(Integer.toString(Params.MIN_REPRODUCE_ENERGY));
	 			}
	 		});
	 		Label cloverLabel = new Label("Clovers created per timestep: ");
	 		cloverLabel.setStyle(costLabelStyle);
	 		cloverLabel.setWrapText(true);
	 		
	 		TextField cloverInput = new TextField();
	 		cloverInput.setPromptText(Integer.toString(Params.REFRESH_CLOVER_COUNT));
	 		cloverInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button cloverButton = new Button();
	 		cloverButton.setText("Update");
	 		cloverButton.setMinHeight(SCREEN_HEIGHT/22);
	 		cloverButton.setStyle(LIGHT_BLUE);
	 		cloverButton.setOnMouseEntered(value->{
	 			cloverButton.setStyle(DARK_BLUE);
	 		});
	 		cloverButton.setOnMouseExited(value->{
	 			cloverButton.setStyle(LIGHT_BLUE);
	 		});
	 		cloverButton.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.REFRESH_CLOVER_COUNT = Integer.parseInt(cloverInput.getText());
	 				cloverInput.clear();
	 				cloverInput.setPromptText(Integer.toString(Params.REFRESH_CLOVER_COUNT));
	 			}
	 		});
	 		Label photoSynthLabel = new Label("Energy gained from Photosynthesis: ");
	 		photoSynthLabel.setStyle(costLabelStyle);
	 		photoSynthLabel.setWrapText(true);
	 		
	 		TextField photoSynthInput = new TextField();
	 		photoSynthInput.setPromptText(Integer.toString(Params.PHOTOSYNTHESIS_ENERGY_AMOUNT));
	 		photoSynthInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button photoSynthButton = new Button();
	 		photoSynthButton.setText("Update");
	 		photoSynthButton.setMinHeight(SCREEN_HEIGHT/22);
	 		photoSynthButton.setStyle(LIGHT_BLUE);
	 		photoSynthButton.setOnMouseEntered(value->{
	 			photoSynthButton.setStyle(DARK_BLUE);
	 		});
	 		photoSynthButton.setOnMouseExited(value->{
	 			photoSynthButton.setStyle(LIGHT_BLUE);
	 		});
	 		photoSynthButton.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.PHOTOSYNTHESIS_ENERGY_AMOUNT = Integer.parseInt(photoSynthInput.getText());
	 				photoSynthInput.clear();
	 				photoSynthInput.setPromptText(Integer.toString(Params.PHOTOSYNTHESIS_ENERGY_AMOUNT));
	 			}
	 		});
	 		
	 		Label startLabel = new Label("Start Energy: ");
	 		startLabel.setStyle(costLabelStyle);
	 		
	 		TextField startInput = new TextField();
	 		startInput.setPromptText(Integer.toString(Params.START_ENERGY));
	 		startInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button startCostButton = new Button();
	 		startCostButton.setText("Update");
	 		startCostButton.setMinHeight(SCREEN_HEIGHT/22);
	 		startCostButton.setStyle(LIGHT_BLUE);
	 		startCostButton.setOnMouseEntered(value->{
	 			startCostButton.setStyle(DARK_BLUE);
	 		});
	 		startCostButton.setOnMouseExited(value->{
	 			startCostButton.setStyle(LIGHT_BLUE);
	 		});
	 		
	 		startCostButton.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.START_ENERGY = Integer.parseInt(startInput.getText());
	 				startInput.clear();
	 				startInput.setPromptText(Integer.toString(Params.START_ENERGY));
	 			}
	 		});
	 		
	 		Label lookLabel = new Label("Look Energy Cost: ");
	 		lookLabel.setStyle(costLabelStyle);
	 		
	 		TextField lookInput = new TextField();
	 		lookInput.setPromptText(Integer.toString(Params.LOOK_ENERGY_COST));
	 		lookInput.setAlignment(Pos.CENTER_RIGHT);
	 		
	 		Button lookButton = new Button();
	 		lookButton.setText("Update");
	 		lookButton.setMinHeight(SCREEN_HEIGHT/22);
	 		lookButton.setStyle(LIGHT_BLUE);
	 		lookButton.setOnMouseEntered(value->{
	 			lookButton.setStyle(DARK_BLUE);
	 		});
	 		lookButton.setOnMouseExited(value->{
	 			lookButton.setStyle(LIGHT_BLUE);
	 		});
	 		lookButton.setOnAction(new EventHandler<ActionEvent>() {
	 			@Override
	 			public void handle(ActionEvent event) {
	 				Params.LOOK_ENERGY_COST = Integer.parseInt(lookInput.getText());
	 				lookInput.clear();
	 				lookInput.setPromptText(Integer.toString(Params.LOOK_ENERGY_COST));
	 			}
	 		});
	 		// Add everything to the params Pane
	 		paramsPane.setVgap(50);
	 		paramsPane.add(paramsLabel, 0, 0);
	 		
	 		paramsPane.add(walkCostLabel, 0, 1);
	 		paramsPane.add(walkCostInput, 1, 1);
	 		paramsPane.add(changeWalkCost, 2, 1);
	 		
	 		paramsPane.add(runCostLabel, 0, 2);
	 		paramsPane.add(runCostInput, 1, 2);
	 		paramsPane.add(changeRunCost, 2, 2);
	 		
	 		paramsPane.add(restCostLabel, 0, 3);
	 		paramsPane.add(restCostInput, 1, 3);
	 		paramsPane.add(changeRestCost, 2, 3);
	 		
	 		paramsPane.add(reproduceLabel, 0, 4);
	 		paramsPane.add(reproduceInput, 1, 4);
	 		paramsPane.add(changeReproduce, 2, 4);
	 		
	 		paramsPane.add(cloverLabel, 0, 5);
	 		paramsPane.add(cloverInput, 1, 5);
	 		paramsPane.add(cloverButton, 2, 5);
	 		
	 		paramsPane.add(photoSynthLabel, 0, 6);
	 		paramsPane.add(photoSynthInput, 1, 6);
	 		paramsPane.add(photoSynthButton, 2, 6);
	 		
	 		paramsPane.add(startLabel, 0, 7);
	 		paramsPane.add(startInput, 1, 7);
	 		paramsPane.add(startCostButton, 2, 7);
	 		
	 		paramsPane.add(lookLabel, 0, 8);
	 		paramsPane.add(lookInput, 1, 8);
	 		paramsPane.add(lookButton, 2, 8);
	 		
	 		HBox topBox = new HBox(spacePane1, paramsPane, spacePane2); 
	 		
	 		editPane.add(topBox, 0, 0);
	 		
	 		// Sets the sizes of buttons and labels
	 		for(int i = 0; i < paramsPane.getChildren().size(); i++) {
	 			if(GridPane.getColumnIndex(paramsPane.getChildren().get(i)) == 2) {
	 				((Button) paramsPane.getChildren().get(i)).setMinWidth(SCREEN_WIDTH* 3/20);
	 			}
	 			if(GridPane.getColumnIndex(paramsPane.getChildren().get(i)) == 1) {
	 				((TextField) paramsPane.getChildren().get(i)).setMinWidth(SCREEN_WIDTH* 3/10);
	 			}
	 		}
	 		
	 	}
	 //}
}