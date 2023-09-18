package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Controller;

public class CorridorController implements Controller {

  private static CorridorController instance;

  public static CorridorController getInstance() {
    return instance;
  }

  private BooleanProperty forwardPressed = new SimpleBooleanProperty();
  private BooleanProperty leftPressed = new SimpleBooleanProperty();
  private BooleanProperty backwardPressed = new SimpleBooleanProperty();
  private BooleanProperty rightPressed = new SimpleBooleanProperty();

  private BooleanBinding keyPressed = forwardPressed.or(leftPressed)
      .or(backwardPressed).or(rightPressed);

  private int movementSpeed = 2;
  @FXML
  private Polygon polygon;
  @FXML
  private Group group;

  @FXML

  private Rectangle player;
  @FXML
  private Rectangle treasureChest;
  @FXML
  private Rectangle door1;
  @FXML
  private Rectangle door2;
  @FXML
  private Rectangle door3;

  @FXML
  private ImageView sword;
  @FXML
  private Pane room;

  @FXML
  private Label lblTime;

  @FXML
  private ComboBox<String> inventoryChoiceBox;

  private AnimationTimer playerTimer = new AnimationTimer() {

    @Override
    public void handle(long timestamp) {
      // updateInventory();
      double bottomRightX = player.getX() + player.getWidth();
      double bottomRightY = player.getY() + player.getHeight();

      if (forwardPressed.get()) {
        if ((polygon.contains(player.getX(), player.getY() - movementSpeed))
            && (polygon.contains(bottomRightX, bottomRightY - movementSpeed))) {

          player.setY(player.getY() - movementSpeed);
        }
      }

      if (leftPressed.get()) {
        if (polygon.contains(player.getX() - movementSpeed, player.getY())
            && polygon.contains(bottomRightX - movementSpeed, bottomRightY)) {
          player.setX(player.getX() - movementSpeed);
        }
      }

      if (backwardPressed.get()) {
        if ((polygon.contains(player.getX(), player.getY() + movementSpeed))
            && polygon.contains(bottomRightX, bottomRightY + movementSpeed)) {
          player.setY(player.getY() + movementSpeed);
        }
      }

      if (rightPressed.get()) {
        if ((polygon.contains(player.getX() + movementSpeed, player.getY()))
            && polygon.contains(bottomRightX + movementSpeed, bottomRightY)) {
          player.setX(player.getX() + movementSpeed);
        }
      }
    }
  };

  private AnimationTimer collisionTimer = new AnimationTimer() {
    @Override
    public void handle(long timestamp) {
      checkCollision();
    }
  };

  public void initialize() {
    // group.setClip(polygon);
    System.out.println(polygon.computeAreaInScreen());
    System.out.println(" ");
    System.out.println(polygon.boundsInLocalProperty());
    System.out.println(" ");
    System.out.println(polygon.boundsInParentProperty());
    System.out.println(" ");
    System.out.println(polygon.getBoundsInLocal());
    System.out.println(" ");
    System.out.println(polygon.getBoundsInParent());
    System.out.println(" ");
    System.out.println(polygon.getLayoutBounds());
    // player.setClip(polygon);
    instance = this;
    keyPressed.addListener((observable, boolValue, randomVar) -> {
      if (!boolValue) {
        playerTimer.start();
        collisionTimer.start();
      } else {
        playerTimer.stop();
        collisionTimer.stop();
      }
    });
  }

  private void checkCollision() {
    double x_center = player.getX() + (player.getWidth() / 2);
    double y_center = player.getY() + (player.getHeight() / 2);

    if (!polygon.contains(x_center, y_center)) {
      System.out.println("daeee");
    }
    if (!polygon.getLayoutBounds().contains(player.getLayoutBounds())) {
      System.out.println("collision");
    }
    if (!polygon.getLayoutBounds().intersects(player.getLayoutBounds())) {
      System.out.println("colsion");
    }
    if (!player.getBoundsInParent().intersects(polygon.getBoundsInParent())) {
      // Collision detected
      System.out.println("dfwww");
      // Handle the collision as needed
    }

    if (!polygon.contains(player.getX(), player.getY())) {
      System.out.println("cn");
    }

    if (!polygon.contains(player.getTranslateX(), player.getTranslateY())) {
      System.out.println("cunt");
    }
    /*
     * // hit left wall
     * if (player.getBoundsInParent().intersects(left.getBoundsInParent())) {
     * stopMovement();
     * player.setLayoutX(left.getLayoutX());
     * player.setX(left.getX() + left.getWidth() + 1);
     * }
     * 
     * // hit right wall
     * if (player.getBoundsInParent().intersects(right.getBoundsInParent())) {
     * stopMovement();
     * player.setLayoutX(right.getLayoutX());
     * player.setX(right.getX() - player.getWidth() - 1);
     * }
     * 
     * // hit top wall
     * if (player.getBoundsInParent().intersects(top.getBoundsInParent())) {
     * stopMovement();
     * player.setLayoutY(top.getLayoutY());
     * player.setY(top.getY() + top.getHeight() + 1);
     * }
     * 
     * // hit bottom wall
     * if (player.getBoundsInParent().intersects(bottom.getBoundsInParent())) {
     * stopMovement();
     * player.setLayoutY(bottom.getLayoutY());
     * player.setY(bottom.getY() - player.getHeight() - 1);
     * }
     */

    // hit door1
    /*
     * if (player.getBoundsInParent().intersects(door1.getBoundsInParent())) {
     * try {
     * stopMovement();
     * App.setRoot(SceneManager.AppUi.PUZZLEROOM);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * // hit door2
     * if (player.getBoundsInParent().intersects(door2.getBoundsInParent())) {
     * try {
     * stopMovement();
     * App.setRoot(SceneManager.AppUi.FIRST_ROOM);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * // hit door3
     * if (player.getBoundsInParent().intersects(door3.getBoundsInParent())) {
     * try {
     * stopMovement();
     * App.setRoot(SceneManager.AppUi.UNTANGLE);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */

  }

  private void stopMovement() {
    forwardPressed.set(false);
    leftPressed.set(false);
    backwardPressed.set(false);
    rightPressed.set(false);
  }

  @FXML
  public void onKeyPressed(KeyEvent event) {
    switch (event.getCode()) {
      case W:
        forwardPressed.set(true);
        break;
      case A:
        leftPressed.set(true);
        break;
      case S:
        backwardPressed.set(true);
        break;
      case D:
        rightPressed.set(true);
        break;
      default:
        break;
    }
  }

  @FXML
  public void onKeyReleased(KeyEvent event) {
    switch (event.getCode()) {
      case W:
        forwardPressed.set(false);
        break;
      case A:
        leftPressed.set(false);
        break;
      case S:
        backwardPressed.set(false);
        break;
      case D:
        rightPressed.set(false);
        break;
      default:
        break;
    }
  }

  @FXML
  public void onTreasureChestClicked(MouseEvent event) {
    System.out.println("clicked");
    String selectedItem = inventoryChoiceBox.getSelectionModel().getSelectedItem();
    if (GameState.isLock2Unlocked == true && GameState.isLock1Unlocked == true) {
      sword.setVisible(true);
      sword.setDisable(false);
      sword.toFront();

    }
    if (selectedItem != null) {
      if (selectedItem.contains("key1")) {
        Inventory.removeFromInventory(selectedItem);

        GameState.isLock1Unlocked = true;
      } else if (selectedItem.contains("key2")) {
        Inventory.removeFromInventory(selectedItem);
        GameState.isLock2Unlocked = true;
      }
    }

  }

  @FXML
  public void onSwordClicked(MouseEvent event) {
    Inventory.addToInventory("sword");
    sword.setVisible(false);
    sword.setDisable(true);
  }

  @FXML
  private void clickExit(MouseEvent event) {
    System.exit(0);
  }

  public void updateInventory() {
    inventoryChoiceBox.setItems(Inventory.getInventory());
  }

  @FXML
  public void updateTimerLabel(String time) {
    lblTime.setText(time);
  }
}