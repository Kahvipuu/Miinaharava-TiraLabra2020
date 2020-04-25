package minesweeper.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class StartSelectView {
    private VBox vbox;
    private GameView gameView;
    private StackPane stackPane;
    private boolean seedSet;
    private long seed;
    
    private int cheight = 5;
    private int cwidth = 5;
    private int cmines = 5;

    private boolean isValidBoard = true;

    private TextField customHeight;
    private TextField customWidth;
    private TextField customMines;

    private final String guideText1 = "Height (min 3, max 50)";
    private final String guideText2 = "Width (min 3, max 50)";
    private final String guideText3 = "Mine count";

    private Label customErrorLabel1;
    private Label customErrorLabel2;
    private Label customErrorLabel3;
                    
    public StartSelectView() {
        Button[] buttons = new Button[] {
            this.initButton("Beginner", 10, 10, 10), 
            this.initButton("Intermediate", 16, 16, 40),
            this.initButton("Expert", 16, 30, 99)
        };
        HBox hbox = new HBox(buttons);

        ToggleButton customBoard = new ToggleButton("Set Custom Board");
        customBoard.getStyleClass().add("menu-button");
        
        customHeight = new TextField("5");
        customHeight.getStyleClass().add("custom-textfield");
        customHeight.setVisible(false);
        customErrorLabel1 = new Label(guideText1);
        customErrorLabel1.setVisible(false);

        customWidth = new TextField("5");
        customWidth.getStyleClass().add("custom-textfield");
        customWidth.setVisible(false);
        customErrorLabel2 = new Label(guideText2);
        customErrorLabel2.setVisible(false);

        customMines = new TextField("5");
        customMines.getStyleClass().add("custom-textfield");
        customMines.setVisible(false);
        customErrorLabel3 = new Label(guideText3);
        customErrorLabel3.setVisible(false);

        Button acceptButton = createAcceptCustomButton("Use Custom Board");
        acceptButton.setVisible(false); 
        
        customBoard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent event) {
                if (allCustomTextFieldsNumeric() 
                        && textCanBeParsed(customHeight.getText(), ("" + Integer.MAX_VALUE).length()) 
                        && textCanBeParsed(customWidth.getText(), ("" + Integer.MAX_VALUE).length()) 
                        && textCanBeParsed(customMines.getText(), ("" + Integer.MAX_VALUE).length())) {
                    cheight = Integer.parseInt(customHeight.getText());
                    cwidth = Integer.parseInt(customWidth.getText());   
                    cmines = Integer.parseInt(customMines.getText());
                }   
                if (customBoard.isSelected()) {
                    customHeight.setVisible(true);
                    customErrorLabel1.setVisible(true);
                    customWidth.setVisible(true);    
                    customErrorLabel2.setVisible(true);
                    customMines.setVisible(true);  
                    customErrorLabel3.setVisible(true); 
                    acceptButton.setVisible(true);            
                } else {
                    customHeight.setVisible(false);
                    customErrorLabel1.setVisible(false); 
                    customWidth.setVisible(false);
                    customErrorLabel2.setVisible(false);
                    customMines.setVisible(false);          
                    customErrorLabel3.setVisible(false);
                    acceptButton.setVisible(false);    
                }
            }   
        });

        ToggleButton seedToggle = new ToggleButton("Use a pre-set seed");
        seedToggle.getStyleClass().add("menu-button");
        TextField seedText = new TextField("1234");
        seedText.getStyleClass().add("custom-textfield");
        seedText.setVisible(false);
        Label seedErrorLabel = new Label("");
        seedErrorLabel.setVisible(false);

        // Create event handler for toggling the pre-set seed on and off
        seedToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean isNumeric = seedText.getText().chars().allMatch(Character::isDigit);
                if (isNumeric && textCanBeParsed(seedText.getText(), ("" + Long.MAX_VALUE).length())) {
                    seed = Long.parseLong(seedText.getText());
                }
                if (seedToggle.isSelected()) {
                    seedText.setVisible(true); 
                    seedErrorLabel.setVisible(true); 
                    seedSet = true;
                } else {
                    seedText.setVisible(false);
                    seedErrorLabel.setVisible(false); 
                    seedSet = false;
                }
            }
        });

        // Check on each keypress if the text in the seed TextField is numeric
        seedText.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isNumeric = seedText.getText().chars().allMatch(Character::isDigit);
            if (!isNumeric 
                    || seedText.getText().isEmpty() 
                    || !textCanBeParsed(seedText.getText(), ("" + Long.MAX_VALUE).length())) {
                seedErrorLabel.setText("Seed must be an positive long value!");
            } else {
                seedErrorLabel.setText("");
                seed = Long.parseLong(newValue);
            }
        });

        checkCustomTextField("height", customHeight, customErrorLabel1);
        checkCustomTextField("width", customWidth, customErrorLabel2);
        checkCustomTextField("mines", customMines, customErrorLabel3);
        
        HBox customHBox0 = new HBox(customBoard);
        HBox customHBox1 = new HBox(customHeight, customErrorLabel1);
        HBox customHBox2 = new HBox(customWidth, customErrorLabel2);
        HBox customHBox3 = new HBox(customMines, customErrorLabel3);
        HBox acceptHBox = new HBox(acceptButton);
        VBox customInput = new VBox(customHBox0, customHBox1, customHBox2, customHBox3, acceptHBox);
        customInput.setVisible(true);

        HBox seedHBox = new HBox(seedToggle, seedText, seedErrorLabel);
        Label gameType = new Label("Select game type");
        gameType.getStyleClass().add("label-header");
        this.vbox = new VBox(gameType, hbox, new Separator(), seedHBox, customInput);
        this.stackPane = new StackPane(this.vbox);
    }
    
    /**
     * Button that initiates a new game with the difficulty (based on size of the
     * board)
     */
    private Button initButton(String label, int height, int width, int mines) {
        Button button = new Button(label);
        button.getStyleClass().add("menu-button");
        button.setOnMouseClicked(e -> {
            this.vbox.setVisible(false);
            Button newGameButton = new Button("New Game");
            newGameButton.setOnMouseClicked(ev -> {
                this.stackPane.getChildren().remove(1);
                this.vbox.setVisible(true);
            });
            if (this.seedSet) {
                this.gameView = new GameView(width, height, new VBox(newGameButton), mines, this.seed);
            } else {
                this.gameView = new GameView(width, height, new VBox(newGameButton), mines);
            }
            this.stackPane.getChildren().add(gameView.getView());
        });
        button.setWrapText(false);
        return button;
    }

    /**
     * Creates a button for initializing a custom minefield
     */
    private Button createAcceptCustomButton(String label) {
        Button button = new Button(label);
        button.getStyleClass().add("menu-button");
        button.setOnMouseClicked(e -> {
            if (isValidBoard) {
                this.vbox.setVisible(false);
                Button newGameButton = new Button("New Game");
                newGameButton.setOnMouseClicked(ev -> {
                    this.stackPane.getChildren().remove(1);
                    this.vbox.setVisible(true);
                });
                if (this.seedSet) {
                    this.gameView = new GameView(cwidth, cheight, new VBox(newGameButton), cmines, this.seed);
                } else {
                    this.gameView = new GameView(cwidth, cheight, new VBox(newGameButton), cmines);
                }
                this.stackPane.getChildren().add(gameView.getView());
            }
        });
        return button;
    }
    
    /**
     * Return the ObservableList of the root node of this class, used for resizing
     * purposes in the App class
     * @return ObservableList
     */
    public ObservableList<Node> rootChildren() {
        return this.stackPane.getChildren();
    }

    /**
     * Returns the underlying StackPane on which the rest of the elements are added.
     * @return StackPane
     */
    public StackPane get() {
        return this.stackPane;
    }

    /**
    * Checks that custom sized minesweeper is a reasonably playable one.
    */
    private void checkCustomTextField(String type, TextField input, Label error) {
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            Boolean isNumeric = input.getText().chars().allMatch(Character::isDigit);
            if (!isNumeric 
                    || input.getText().isEmpty() 
                    || !textCanBeParsed(input.getText(), ("" + Integer.MAX_VALUE).length())) {
                error.setText("The " + type + " must be an integer!");
                error.getStyleClass().add("label-failure");
                customErrorLabel3.setText(guideText3);            
                customErrorLabel3.getStyleClass().removeAll("label-failure");
                isValidBoard = false;
            } else {
                if (!type.equals("mines")) {
                    int value = Integer.parseInt(input.getText());
                    if (value > 50) {
                        
                        error.setText("Maximum " + type + " is 50");
                        error.getStyleClass().add("label-failure");
                        customErrorLabel3.setText(guideText3);            
                        customErrorLabel3.getStyleClass().removeAll("label-failure");
                        isValidBoard = false;
                    } else if (value < 3) {
                        error.setText("Minimum " + type + " is 3");
                        error.getStyleClass().add("label-failure");
                        customErrorLabel3.setText(guideText3);            
                        customErrorLabel3.getStyleClass().removeAll("label-failure");
                        isValidBoard = false;
                    } else {
                        if (type.equals("height")) {
                            cheight = value;
                            error.setText(guideText1);
                            error.getStyleClass().removeAll("label-failure");
                        } else if (type.equals("width")) {
                            cwidth = value;
                            error.setText(guideText2);
                            error.getStyleClass().removeAll("label-failure");
                        }
                       
                        checkMines();
                    }
                    
                } else {
                    checkMines();
                }
            }
        });
    }
    /**
     * Checks that the amount of mines for a possible custom board is legal
     */
    private void checkMines() {
        if (customMines.getText().chars().allMatch(Character::isDigit) && !customMines.getText().isEmpty()) {
            cmines = Integer.parseInt(customMines.getText());
        } else {
            isValidBoard = false;
            return;
        }
        if (cmines > ((cheight * cwidth) - 9)) {
            isValidBoard = false;
            customErrorLabel3.setText("Too many mines!");
            customErrorLabel3.getStyleClass().add("label-failure");
        } else {
            if (allCustomTextFieldsNumeric() && heightAndWidthAreInRange()) { 
                isValidBoard = true;
            }

            customErrorLabel3.setText(guideText3);            
            customErrorLabel3.getStyleClass().removeAll("label-failure");
        }
    }
    
    /**
     * Checks that every custom board size value is a numeric value
     */
    private Boolean allCustomTextFieldsNumeric() {
        return ((customHeight.getText().chars().allMatch(Character::isDigit) && !customHeight.getText().isEmpty())
            && (customWidth.getText().chars().allMatch(Character::isDigit) && !customWidth.getText().isEmpty())
            && (customMines.getText().chars().allMatch(Character::isDigit) && !customMines.getText().isEmpty()));
    }

    /**
     * Checks that the board height and width are within parameters
     */
    private Boolean heightAndWidthAreInRange() {
        int heightvalue = Integer.parseInt(customHeight.getText());
        int widthvalue = Integer.parseInt(customWidth.getText());

        return (heightvalue >= 3 && heightvalue <= 50 && widthvalue >= 3 && widthvalue <= 50);
    }

    private boolean textCanBeParsed(String text, int maxLength) {
        return text.length() <= maxLength;
    }
}
