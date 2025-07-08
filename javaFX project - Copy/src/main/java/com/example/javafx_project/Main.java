package com.example.javafx_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    Button goBack1, goBack2, goBack3, goBack4, goBack5, addTxt, addManualweapon, list, remove, combine, exit;
    Stage window;
    Scene mainMenu, addTxtscene, addManualScene, listScene, removeScene, combineScene, exitScene;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("WEAPON DMS");

        ListView<weapon> weaponListView = new ListView<>();
        ListView<weapon> weaponListView2 = new ListView<>();
        ListView<weapon> weaponListView3 = new ListView<>();
        ArrayList<weapon> weaponList = new ArrayList<>();

        weapon busterSword = new weapon();
        weapon leatherGlove = new weapon();
        weapon gatlingGun = new weapon();

        weaponList.add(busterSword);
        weaponList.add(leatherGlove);
        weaponList.add(gatlingGun);

        weaponListView.getItems().add(busterSword);
        weaponListView.getItems().add(leatherGlove);
        weaponListView.getItems().add(gatlingGun);

        weaponListView2.getItems().add(busterSword);
        weaponListView2.getItems().add(leatherGlove);
        weaponListView2.getItems().add(gatlingGun);

        weaponListView3.getItems().add(busterSword);
        weaponListView3.getItems().add(leatherGlove);
        weaponListView3.getItems().add(gatlingGun);

        busterSword.setName("Buster Sword");
        busterSword.setType("Sword");
        busterSword.setDmgType("Slash");
        busterSword.setPrice(200);
        busterSword.setDmgVal(18);
        busterSword.setHitRate(96);

        leatherGlove.setName("Leather Glove");
        leatherGlove.setType("Fist");
        leatherGlove.setDmgType("Strike");
        leatherGlove.setPrice(120);
        leatherGlove.setDmgVal(13);
        leatherGlove.setHitRate(99);

        gatlingGun.setName("Gatling Gun");
        gatlingGun.setType("Gun");
        gatlingGun.setDmgType("Pierce");
        gatlingGun.setPrice(160);
        gatlingGun.setDmgVal(14);
        gatlingGun.setHitRate(97);

        //Main Menu
        Label label = new Label("\tWEAPON INVENTORY" +
                "\n\tPlease select what action you would like to perform!");

        addTxt = new Button("Add New Weapon From Text File");
        addTxt.setOnAction(e -> window.setScene(addTxtscene));

        addManualweapon = new Button("Add New Weapon Manually");
        addManualweapon.setOnAction(e -> window.setScene(addManualScene));

        list = new Button("List weapons");
        list.setOnAction(e -> window.setScene(listScene));

        remove = new Button("Remove Weapon");
        remove.setOnAction(e -> window.setScene(removeScene));

        combine = new Button("Combine Weapons");
        combine.setOnAction(e -> window.setScene(combineScene));

        exit = new Button("Exit Program");
        exit.setOnAction(e -> closeProgram());

        //Add Weapon txt Screen
        Label weaponTxtLabel = new Label("\tADD NEW WEAPON FROM TEXT FILE");
        Label typeDirLabel = new Label("Please type the directory of the file containing the new weapon.");
        Label result = new Label("");
        TextField dir = new TextField();

        Button typeDirButton = new Button("Submit");
        typeDirButton.setOnAction(e -> {
            File file = new File(dir.getText());
            String str, name, type, dmgType;
            int price;
            double dmgVal;
            double hitRate;
            boolean error = false;

            Scanner x = null;
            weapon weapon1 = new weapon();

            try {
                x = new Scanner(file);
                result.setText("Successfully read the file!");
                error = false;
            } catch (FileNotFoundException a) {
                result.setText("\n\tFile not found "+ "\n\tPlease try again\n");
                error = true;
            }
                if (error != true) {
                    while (x.hasNext()) {
                        str = x.nextLine();
                        String[] tokens = str.split("-");


                        name = tokens[0];
                        type = tokens[1];
                        dmgType = tokens[2];
                        price = Integer.valueOf(tokens[3]);
                        dmgVal = Double.valueOf(tokens[4]);
                        hitRate = Double.valueOf(tokens[5]);

                        weapon1.setName(name);
                        weapon1.setType(type);
                        weapon1.setDmgType(dmgType);
                        weapon1.setPrice(price);
                        weapon1.setDmgVal(dmgVal);
                        weapon1.setHitRate(hitRate);
                    }

                    weaponList.add(weapon1);
                    weaponListView.getItems().add(weapon1);
                    weaponListView2.getItems().add(weapon1);
                    weaponListView3.getItems().add(weapon1);
                }
        });


        goBack1 = new Button("Return");
        goBack1.setOnAction(e -> window.setScene(mainMenu));

        //Add Weapon Manually screen
        Label addManualLabel = new Label("\tADD NEW WEAPON MANUALLY");
        Label manualResult = new Label("");

        Label enterName = new Label("Enter the name of the new weapon.");
        TextField wpnName = new TextField();

        Label enterType = new Label("Select the type of weapon.");
        ComboBox selType = new ComboBox();
        selType.getItems().add("Sword");
        selType.getItems().add("Fist");
        selType.getItems().add("Gun");

        Label enterDmgType = new Label("Select the weapon's damage type.");
        ComboBox selDmgType = new ComboBox();
        selDmgType.getItems().add("Slash");
        selDmgType.getItems().add("Strike");
        selDmgType.getItems().add("Pierce");

        Label enterPrice = new Label("Enter the weapon's price");
        TextField wpnPrice = new TextField();

        Label enterDmgVal = new Label("Enter the amount of damage the weapon will do.");
        TextField wpnDmg = new TextField();

        Label enterHitRate = new Label ("Enter the weapon's hit rate. \nMust be a value between 0 - 100.");
        TextField wpnHit = new TextField();

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            boolean error = false;

            int price = Integer.parseInt(wpnPrice.getText());
            double dmgVal = Double.parseDouble(wpnDmg.getText());
            double hitRate = Double.parseDouble(wpnHit.getText());
            weapon weapon1 = new weapon();

            if (hitRate < 0 || hitRate > 100) {
                error = true;
                manualResult.setText("Error!: Hit rate entered must be a value between 0 - 100!");
            }

            if (error != true){
                weapon1.setName(wpnName.getText());
                weapon1.setType((String) selType.getValue());
                weapon1.setDmgType((String) selDmgType.getValue());
                weapon1.setPrice(price);
                weapon1.setDmgVal(dmgVal);
                weapon1.setHitRate(hitRate);
                weaponList.add(weapon1);
                weaponListView.getItems().add(weapon1);
                weaponListView2.getItems().add(weapon1);
                weaponListView3.getItems().add(weapon1);


                manualResult.setText(wpnName.getText() + " was successfully added!");
            }
        });

        goBack2 = new Button("Return");
        goBack2.setOnAction(e -> window.setScene(mainMenu));

        //List weapon Screen
        Label listLabel = new Label("\tLIST WEAPONS");
        goBack3 = new Button("Return");
        goBack3.setOnAction(e -> window.setScene(mainMenu));

        //Remove weapon Screen
        Label removeLabel = new Label("REMOVE WEAPON");
        Label removeLabel2 = new Label("Please type the name of the weapon to remove.");
        Label removeResult = new Label("");
        TextField removeText = new TextField();

        Button removeButton = new Button("Submit");
        removeButton.setOnAction(e -> {
            weapon weapon1 = new weapon();
            String weaponName = removeText.getText();
            boolean error = true;

            for (int i = 0; i < weaponList.size(); i++) {
                if (Objects.equals(weaponName, weaponList.get(i).getName())) {
                    error = false;
                    weaponList.remove(weaponList.get(i));
                    weaponListView.getItems().remove(i);
                    weaponListView2.getItems().remove(i);
                    weaponListView3.getItems().remove(i);
                    removeResult.setText(weaponName + " successfully removed.");
                }
            }

            if (error){
                removeResult.setText("Error: No weapon with that name was found. \nPlease check spelling/capitalization and try again.");
            }
        });

        goBack4 = new Button("Return");
        goBack4.setOnAction(e -> window.setScene(mainMenu));

        // Combine weapon Screen
        Label combineLabel = new Label("\tCOMBINE WEAPON");
        Label combineResult1 = new Label("");
        Label combineResult2 = new Label("");
        Label combineSuccess = new Label("");

        Label combineWpn1 = new Label("Please enter the name of the first weapon you would like to use.");
        TextField wpn1 = new TextField();

        Label combineWpn2 = new Label("Please enter the name of the second weapon you would like to use.");
        TextField wpn2 = new TextField();

        Button combineButton = new Button("Submit");
        combineButton.setOnAction(e -> {
            weapon weapon1 = new weapon();
            weapon weapon2 = new weapon();

            String weaponName1 = wpn1.getText();
            String weaponName2 = wpn2.getText();
            boolean error1 = true;
            boolean error2 = true;

            for (int i = 0; i < weaponList.size(); i++) {
                if (Objects.equals(weaponName1, weaponList.get(i).getName())) {
                    error1 = false;
                    weapon1 = weaponList.get(i);
                    combineResult1.setText(weaponName1 + " was selected.");
                }
            }

            if (error1){
                combineResult1.setText("Error: No weapon with that name was found. \nPlease check spelling/capitalization and try again.");
            }

            for (int i = 0; i < weaponList.size(); i++) {
                if (Objects.equals(weaponName2, weaponList.get(i).getName())) {
                    error2 = false;
                    weapon2 = weaponList.get(i);
                    combineResult2.setText(weaponName2 + " was selected.");
                }
            }

            if (error2){
                combineResult2.setText("Error: No weapon with that name was found. \nPlease check spelling/capitalization and try again.");
            }

            if (!error1 && !error2){
                weapon fusedWeapon = new weapon();

                fusedWeapon.setName(weapon1.getName() + " + " + weapon2.getName());
                fusedWeapon.setDmgType(weapon1.getDmgType());
                fusedWeapon.setType(weapon1.getType());
                fusedWeapon.setPrice(weapon1.getPrice() + weapon2.getPrice());
                fusedWeapon.setDmgVal((weapon1.getDmgVal() + weapon2.getDmgVal()) / 2);
                fusedWeapon.setHitRate((weapon1.getHitRate() + weapon2.getHitRate()) / 2);

                weaponList.add(fusedWeapon);
                weaponListView.getItems().add(fusedWeapon);
                weaponListView2.getItems().add(fusedWeapon);
                weaponListView3.getItems().add(fusedWeapon);
                combineSuccess.setText("\n\tNEW WEAPON SUCCESSFULLY CREATED");
            }
            else{
                combineSuccess.setText("Combine failed...");
            }
        });



        goBack5 = new Button("Return");
        goBack5.setOnAction(e -> window.setScene(mainMenu));



        // Main Menu Display
        VBox mainMenuLayout = new VBox(20);
        mainMenuLayout.getChildren().addAll(label, addTxt, addManualweapon, list, remove, combine, exit);
        mainMenu = new Scene(mainMenuLayout, 800, 600);

        // Add from txt Display
        VBox addTxtLayout = new VBox(20);
        addTxtLayout.getChildren().addAll(weaponTxtLabel, typeDirLabel, dir, typeDirButton, result, goBack1);
        addTxtscene = new Scene(addTxtLayout, 800, 600);

        // Add Manually Display
        VBox addManualLayout = new VBox(20);
        addManualLayout.getChildren().addAll(addManualLabel, enterName, wpnName, enterType, selType, enterDmgType, selDmgType, enterPrice, wpnPrice, enterDmgVal, wpnDmg, enterHitRate, wpnHit, submit, manualResult, goBack2);
        addManualScene = new Scene(addManualLayout, 800, 700);

        // List Display
        VBox listLayout = new VBox(20);
        listLayout.getChildren().addAll(listLabel, weaponListView, goBack3);
        listScene = new Scene(listLayout, 800, 600);

        // Remove Display
        VBox removeLayout = new VBox(20);
        removeLayout.getChildren().addAll(removeLabel, weaponListView2, removeLabel2, removeText, removeButton, removeResult, goBack4);
        removeScene = new Scene(removeLayout, 800, 800);

        // Combine Display
        VBox combineLayout = new VBox(20);
        combineLayout.getChildren().addAll(combineLabel, weaponListView3, combineWpn1, wpn1, combineWpn2, wpn2, combineButton, combineResult1, combineResult2, combineSuccess, goBack5);
        combineScene = new Scene(combineLayout, 800, 900);

        window.setScene(mainMenu);
        window.show();
    }

    public void closeProgram(){
        window.close();
    }
}