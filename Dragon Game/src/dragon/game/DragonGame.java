package dragon.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DragonGame extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, InterruptedException {
        Dragon playerDragon = new Dragon("Your Dragon");
        Dragon enemyDragon = new Dragon("Foe Dragon Fluffnugget");

        // Sets image and object for the background.
        final ImageView selectedImage = new ImageView();
        Image background = new Image(new FileInputStream("images/background_Fotor.png"));
        selectedImage.setImage(background);
        selectedImage.setTranslateY(-75);
        selectedImage.setFitWidth(600);
        selectedImage.setFitHeight(350);

        // Sets image and object for the rain animation
        final ImageView selectedImage4 = new ImageView();
        Image rainEffect = new Image(new FileInputStream("images/rainEffect.gif"));
        selectedImage4.setImage(rainEffect);
        selectedImage4.setTranslateY(-75);
        selectedImage4.setFitWidth(600);
        selectedImage4.setFitHeight(350);

        // Sets image and object for player.
        final ImageView selectedImage1 = new ImageView();
        Image player = new Image(new FileInputStream("images/scarydragon.png"));
        selectedImage1.setImage(player);
        selectedImage1.setTranslateX(-150);

        // Sets image and object for enemy.
        final ImageView selectedImage2 = new ImageView();
        Image enemy = new Image(new FileInputStream("images/dragon.png"));
        selectedImage2.setImage(enemy);
        selectedImage2.setScaleX(.5);
        selectedImage2.setScaleY(.5);
        selectedImage2.setTranslateX(155);
        selectedImage2.setTranslateY(-80);

        // Sets image and object for the background.
        final ImageView selectedImage3 = new ImageView();
        Image GAME_OVER = new Image(new FileInputStream("images/gameoverMoving.gif"));
        selectedImage3.setImage(GAME_OVER);
        selectedImage3.setFitWidth(600);
        selectedImage3.setFitHeight(500);
        selectedImage3.setVisible(false);

        // Sets the text that's displayed at the end of the game (you win/you lose)
        Label winner = new Label();
        winner.setTextFill(Color.YELLOW);
        winner.setScaleX(5);
        winner.setScaleY(5);
        winner.setTranslateY(150);
        winner.setVisible(false);

        // Describes the actions of player 1
        Label eventDescription1 = new Label("Let the battle begin! Select an attack to the right");
        eventDescription1.setTextFill(Color.BLACK);
        eventDescription1.setScaleX(1);
        eventDescription1.setScaleY(1);
        eventDescription1.setMaxSize(150, 100);
        eventDescription1.setTranslateY(150);
        eventDescription1.setTranslateX(205);
        eventDescription1.setWrapText(true);

        // Describes the actions of the enemy
        Label eventDescription2 = new Label("Choose your attacks wisely!");
        eventDescription2.setTextFill(Color.BLACK);
        eventDescription2.setScaleX(1);
        eventDescription2.setScaleY(1);
        eventDescription2.setMaxSize(150, 100);
        eventDescription2.setTranslateY(210);
        eventDescription2.setTranslateX(205);
        eventDescription2.setWrapText(true);

        // Displays the player's name and HP above their dragon
        Label playerHP = new Label(playerDragon.getName() + "'s HP = " + playerDragon.getHP());
        playerHP.setTextFill(Color.YELLOW);
        playerHP.setScaleX(1.5);
        playerHP.setScaleY(1.5);
        playerHP.setTranslateY(-70);
        playerHP.setTranslateX(-150);
        playerHP.setWrapText(true);
        playerHP.setMaxSize(150, 200);

        // Displays the enemy's name and HP above the enemy dragon
        Label enemyHP = new Label(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
        enemyHP.setTextFill(Color.YELLOW);
        enemyHP.setScaleX(1.5);
        enemyHP.setScaleY(1.5);
        enemyHP.setTranslateY(-150);
        enemyHP.setTranslateX(180);
        enemyHP.setWrapText(true);
        enemyHP.setMaxSize(150, 200);

        // Sets up animation for player & enemy attack.
        TranslateTransition playerAttack = new TranslateTransition(Duration.seconds(.25), selectedImage1);
        playerAttack.setCycleCount(2);
        playerAttack.setAutoReverse(true);
        playerAttack.setByX(200);
        playerAttack.setByY(-110);

        TranslateTransition enemyAttack = new TranslateTransition(Duration.seconds(.25), selectedImage2);
        enemyAttack.setCycleCount(2);
        enemyAttack.setAutoReverse(true);
        enemyAttack.setDelay(Duration.seconds(1.5));
        enemyAttack.setByX(-200);
        enemyAttack.setByY(110);

        /* Did not work quite the way I wanted it to - will come back to it later
        ImageView clawAttack = new ImageView();
        Image clawAnimate = new Image(new FileInputStream("images/claw.gif"));
        clawAttack.setImage(clawAnimate);
        clawAttack.setScaleX(.5);
        clawAttack.setScaleY(.5);
        clawAttack.setTranslateX(155);
        clawAttack.setTranslateY(-80);
        clawAttack.setVisible(false);
        
        TranslateTransition clawAttackTrans = new TranslateTransition(Duration.seconds(.25), clawAttack);
        clawAttackTrans.setAutoReverse(true);
        clawAttack.setVisible(true);
        clawAttackTrans.setDelay(Duration.seconds(3));*/
        // Attack button for claw, POSITION INFO: TOP LEFT BUTTON
        Button claw = new Button("Claw");
        claw.setMaxSize(200, 75);
        claw.setTranslateX(-200);
        claw.setTranslateY(140);
        claw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerDragon.attack(Dragon.CLAW, enemyDragon); // Does the actual attack

                playerAttack.play(); // Plays attack animation
                //clawAttackTrans.play();
                eventDescription1.setText(playerDragon.getName() + " used CLAW");
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());

                // Swaps images whenever the game ends
                if (deathCheck(playerDragon, enemyDragon) == true) {
                    selectedImage3.setVisible(true);
                    winner.setVisible(true);
                    playerHP.setVisible(false);
                    enemyHP.setVisible(false);
                    eventDescription1.setVisible(false);
                    eventDescription2.setVisible(false);
                    selectedImage4.setVisible(false);

                    if (playerDragon.getHP() > 0) {
                        winner.setText("You WON!");
                    } else {
                        winner.setText("You LOST!");
                    }
                }

                // Enemy attack
                int attackOutput = enemyTurn(playerDragon, enemyDragon);
                enemyAttack.play();

                // Used for printing out the enemy's actions to the second event label
                if (attackOutput == 0) {
                    eventDescription2.setText(enemyDragon.getName() + " used CLAW");
                } else if (attackOutput == 1) {
                    eventDescription2.setText(enemyDragon.getName() + " used BITE");
                } else if (attackOutput == 2) {
                    eventDescription2.setText(enemyDragon.getName() + " used FIRE BREATH");
                } else if (attackOutput == 3) {
                    eventDescription2.setText(enemyDragon.getName() + " used TAIL WHIP");
                }
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
            }
        });

        // Attack button for bite, POSITION INFO: BOTTOM LEFT BUTTON
        Button bite = new Button("Bite");
        bite.setMaxSize(200, 75);
        bite.setTranslateX(-200);
        bite.setTranslateY(215);
        bite.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerDragon.attack(Dragon.BITE, enemyDragon);
                TranslateTransition attack = new TranslateTransition(Duration.seconds(1), selectedImage1);

                playerAttack.play(); // Plays attack animation
                eventDescription1.setText(playerDragon.getName() + " used BITE");
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());

                // Swaps images whenever the game ends
                if (deathCheck(playerDragon, enemyDragon) == true) {
                    selectedImage3.setVisible(true);
                    winner.setVisible(true);
                    playerHP.setVisible(false);
                    enemyHP.setVisible(false);
                    eventDescription1.setVisible(false);
                    eventDescription2.setVisible(false);
                    selectedImage4.setVisible(false);

                    if (playerDragon.getHP() > 0) {
                        winner.setText("You WON!");
                    } else {
                        winner.setText("You LOST!");
                    }
                }

                // Enemy attack
                int attackOutput = enemyTurn(playerDragon, enemyDragon);
                enemyAttack.play();

                // Used for printing out the enemy's actions to the second event label
                if (attackOutput == 0) {
                    eventDescription2.setText(enemyDragon.getName() + " used CLAW");
                } else if (attackOutput == 1) {
                    eventDescription2.setText(enemyDragon.getName() + " used BITE");
                } else if (attackOutput == 2) {
                    eventDescription2.setText(enemyDragon.getName() + " used FIRE BREATH");
                } else if (attackOutput == 3) {
                    eventDescription2.setText(enemyDragon.getName() + " used TAIL WHIP");
                }
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
            }
        });

        // Attack button for fire breath, POSITION INFO: TOP RIGHT BUTTON
        Button fireBreath = new Button("Fire Breath");
        fireBreath.setMaxSize(200, 75);
        fireBreath.setTranslateX(0);
        fireBreath.setTranslateY(140);
        fireBreath.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerDragon.attack(Dragon.FIRE_BREATH, enemyDragon);

                playerAttack.play(); // Plays attack animation
                eventDescription1.setText(playerDragon.getName() + " used FIRE BREATH");
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());

                // Swaps images whenever the game ends
                if (deathCheck(playerDragon, enemyDragon) == true) {
                    selectedImage3.setVisible(true);
                    winner.setVisible(true);
                    playerHP.setVisible(false);
                    enemyHP.setVisible(false);
                    eventDescription1.setVisible(false);
                    eventDescription2.setVisible(false);
                    selectedImage4.setVisible(false);

                    if (playerDragon.getHP() > 0) {
                        winner.setText("You WON!");
                    } else {
                        winner.setText("You LOST!");
                    }
                }

                // Enemy attack
                int attackOutput = enemyTurn(playerDragon, enemyDragon);
                enemyAttack.play();

                // Used for printing out the enemy's actions to the second event label
                if (attackOutput == 0) {
                    eventDescription2.setText(enemyDragon.getName() + " used CLAW");
                } else if (attackOutput == 1) {
                    eventDescription2.setText(enemyDragon.getName() + " used BITE");
                } else if (attackOutput == 2) {
                    eventDescription2.setText(enemyDragon.getName() + " used FIRE BREATH");
                } else if (attackOutput == 3) {
                    eventDescription2.setText(enemyDragon.getName() + " used TAIL WHIP");
                }
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
            }
        });

        // Attack button for tail whip, POSITION INFO: BOTTOM RIGHT BUTTON
        Button tailWhip = new Button("Tail Whip");
        tailWhip.setMaxSize(200, 75);
        tailWhip.setTranslateX(0);
        tailWhip.setTranslateY(215);
        tailWhip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                playerDragon.attack(Dragon.TAIL_WHIP, enemyDragon);

                playerAttack.play(); // Plays attack animation
                eventDescription1.setText(playerDragon.getName() + " used TAIL WHIP");
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());

                // Swaps images whenever the game ends
                if (deathCheck(playerDragon, enemyDragon) == true) {
                    selectedImage3.setVisible(true);
                    winner.setVisible(true);
                    playerHP.setVisible(false);
                    enemyHP.setVisible(false);
                    eventDescription1.setVisible(false);
                    eventDescription2.setVisible(false);
                    selectedImage4.setVisible(false);

                    if (playerDragon.getHP() > 0) {
                        winner.setText("You WON!");
                    } else {
                        winner.setText("You LOST!");
                    }
                }

                // Enemy attack
                int attackOutput = enemyTurn(playerDragon, enemyDragon);
                enemyAttack.play();

                // Used for printing out the enemy's actions to the second event label
                if (attackOutput == 0) {
                    eventDescription2.setText(enemyDragon.getName() + " used CLAW");
                } else if (attackOutput == 1) {
                    eventDescription2.setText(enemyDragon.getName() + " used BITE");
                } else if (attackOutput == 2) {
                    eventDescription2.setText(enemyDragon.getName() + " used FIRE BREATH");
                } else if (attackOutput == 3) {
                    eventDescription2.setText(enemyDragon.getName() + " used TAIL WHIP");
                }
                playerHP.setText(playerDragon.getName() + "'s HP = " + playerDragon.getHP());
                enemyHP.setText(enemyDragon.getName() + "'s HP = " + enemyDragon.getHP());
            }
        });

        // Creates pane
        StackPane root = new StackPane();
        root.getChildren().addAll(selectedImage, selectedImage2, selectedImage1, claw, bite, fireBreath, tailWhip,
                selectedImage3, selectedImage4, winner, eventDescription1, eventDescription2, playerHP, enemyHP);

        Scene scene = new Scene(root, 600, 500);

        primaryStage.setTitle("Battle Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean deathCheck(Dragon player, Dragon enemy) {
        if (player.getHP() == 0 || enemy.getHP() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Enemy's attack is selected
    public static int enemyTurn(Dragon player, Dragon enemy) {
        Random gen = new Random();
        int caseNum = 0;
        switch (gen.nextInt(4)) {
            case 0:
                enemy.attack(enemy.CLAW, player);
                caseNum = 0;
                break;
            case 1:
                enemy.attack(enemy.BITE, player);
                caseNum = 1;
                break;
            case 2:
                enemy.attack(enemy.FIRE_BREATH, player);
                caseNum = 2;
                break;
            case 3:
                enemy.attack(enemy.TAIL_WHIP, player);
                caseNum = 3;
                break;
        }
        return caseNum;
    }
}
