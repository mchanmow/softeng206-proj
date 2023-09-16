package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.DungeonMaster;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager;

/** Controller class for the room view. */
public class RoomController {

  @FXML
  private Rectangle door;
  @FXML
  private Rectangle window;
  @FXML
  private Rectangle vase;

  @FXML
  private Pane popUp;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title      the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message    the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void clickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");

    if (!GameState.isRiddleResolved) {
      showDialog("Info", "Riddle", "You need to resolve the riddle!");
      App.setRoot(SceneManager.AppUi.CHAT);
      return;
    }

    if (!GameState.isKeyFound) {
      showDialog(
          "Info", "Find the key!", "You resolved the riddle, now you know where the key is.");
    } else {
      showDialog("Info", "You Won!", "Good Job!");
    }
  }

  /**
   * Handles the click event on the vase.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickVase(MouseEvent event) {
    System.out.println("vase clicked");
    if (GameState.isRiddleResolved && !GameState.isKeyFound) {
      showDialog("Info", "Key Found", "You found a key under the vase!");
      GameState.isKeyFound = true;
    }
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickWindow(MouseEvent event) {
    System.out.println("window clicked");
    DungeonMaster dungeonMaster = new DungeonMaster();
    // TODO: change on click method to not update on exit
    Task<Pane> task = new Task<Pane>() {
      @Override
      protected Pane call() throws Exception {
        return dungeonMaster.getText();
      }
    };
    task.setOnSucceeded(e -> {
      System.out.println("home task succeeded");
      Pane dialogue = task.getValue();
      popUp.getChildren().add(dialogue);
      dialogue.getStyleClass().add("popUp");
      dialogue.setOnMouseClicked(event1 -> {
        if (!dungeonMaster.isSpeaking()) {
          dungeonMaster.update();
        }
      });
    });
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
    // dialog.getStyleClass().add("popUp");
    // popUp.getChildren().add(dialog);

  }
}