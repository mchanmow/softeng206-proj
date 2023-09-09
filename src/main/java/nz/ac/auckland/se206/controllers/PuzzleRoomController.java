package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class PuzzleRoomController {

  @FXML
  private void clickPuzzle(MouseEvent event) throws IOException {
    App.setRoot(AppUi.PUZZLE);
  }
}
