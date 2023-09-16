package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.Parent;
import nz.ac.auckland.se206.Controller;

public class SceneManager {
  public enum AppUi {
    START,
    FIRST_ROOM,
    CORRIDOR,
    PUZZLEROOM,
    PUZZLE,
    CHAT,
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();

  private static List<Controller> controllers = new ArrayList<>();

  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  public static Parent getUiRoot(AppUi ui) {
    return sceneMap.get(ui);
  }

  public static List<Controller> getControllers() {
    return controllers;
  }

  public static void addController(Controller controller) {
    controllers.add(controller);
  }

}
