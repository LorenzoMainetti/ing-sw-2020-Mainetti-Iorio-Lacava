package it.polimi.ingsw.PSP41.view.GUIPackage;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class TransitionHandler {
    private static Stage primaryStage;
    private static Scene loadingScene;
    private static Scene loginScene;
    private static Scene gameScene;
    private static Scene godPowerScene;
    private static Scene waitingScene;
    private static Scene cardChoiceScene;
    private static Scene playerScene;
    private static Scene numberScene;


    public static void setPrimaryStage(Stage primaryStage) {
        TransitionHandler.primaryStage = primaryStage;
    }


    //scene setters
    public static void setLoadingScene(Scene loadingScene) {
        TransitionHandler.loadingScene = loadingScene;
    }

    public static void setLoginScene(LoginScene loginScene){
        TransitionHandler.loginScene = new Scene(loginScene.getRoot());
    }

    /*public static void setGameScene(GameScene gameScene) {
        TransitionHandler.gameScene = new Scene(gameScene.getRoot());
    }*/

    public static void setGodPowerScene(GodPowerScene godPowerScene) {
        TransitionHandler.godPowerScene = new Scene(godPowerScene.getRoot());
    }

    public static void setWaitingScene(WaitingScene waitingScene) {
        TransitionHandler.waitingScene = new Scene(waitingScene.getRoot());
    }

    public static void setCardChoiceScene(CardChoiceScene cardChoiceScene) {
        TransitionHandler.cardChoiceScene = new Scene(cardChoiceScene.getRoot());
    }

    public static void setPlayerScene(PlayerScene playerScene) {
        TransitionHandler.playerScene = new Scene(playerScene.getRoot());
    }

    public static void setNumberScene(NumberScene numberscene){
        TransitionHandler.numberScene = new Scene(numberscene.getRoot());
    }


    //go to...
    private static void goTo(Scene scene){
        primaryStage.setScene(scene);
    }

    public static void toLoadingScene(){
        goTo(loadingScene);
    }

    public static void toLoginScene(){
        goTo(loginScene);
    }

    public static void toGodPowerScene(){
        goTo(godPowerScene);
    }

    public static void toWaitingScene(){
        goTo(waitingScene);
    }

    public static void toCardChoiceScene(){
        goTo(cardChoiceScene);
    }

    public static void toPlayerScene(){
        goTo(playerScene);
    }

    public static void toGameScene(){
        goTo(gameScene);
    }

    public static void toNumberScene() { goTo(numberScene); }

}
