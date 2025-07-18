package com.example.javafx_project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


/**
 * Daniel Duran
 * CEN 3024 - Software Development 1
 * July 18, 2025.
 * Main.java
 * This program will allow users to supply and access a database of weapons where they can add new weapons, remove
 * pre-existing ones, display a list of all currently held weapons, update any field of any weapon, and create a new
 * weapon by combining two pre-existing weapons.
 */

public class Main extends Application {

    Button goBack1, goBack2, goBack3, goBack4, goBack5, addManualweapon, list, remove, update, combine, exit;
    Stage window;
    Scene addDB, mainMenu, newDB, addManualScene, listScene, removeScene, updateScene, combineScene;
    String dbURL;
    Connection dbConnect;
    ResultSet result1;
    Statement statement;
    ArrayList<weapon> weaponList = new ArrayList<>();
    ObservableList<weapon> observableList = FXCollections.observableList(weaponList);
    String oldName, oldType, oldDmgType, oldPrice, oldDmgVal, oldHitRate;

    public static void main(String[] args) {
        launch();
    }

    /**
     * Daniel Duran
     * CEN 3024 - Software Development 1
     * July 18, 2025.
     * start.java
     * This method displays the GUI the user will use to perform all the program's functions.
     * @param primaryStage The main stage where all the menus are displayed
     * @throws Exception If a database is not found or if an SQL command fails.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("WEAPON DMS");

        ListView<weapon> weaponListView1 = new ListView<>();
        ListView<weapon> weaponListView2 = new ListView<>();
        ListView<weapon> weaponListView3 = new ListView<>();
        ListView<weapon> weaponListView4 = new ListView<>();


        //Main Menu
        Label label = new Label("\tWEAPON INVENTORY" +
                "\n\tPlease select what action you would like to perform!");

        Button newDBbutton = new Button("Select new database");
        newDBbutton.setOnAction(e -> window.setScene(addDB));

        addManualweapon = new Button("Add New Weapon Manually");
        addManualweapon.setOnAction(e -> window.setScene(addManualScene));

        list = new Button("List weapons");
        list.setOnAction(e -> window.setScene(listScene));

        remove = new Button("Remove Weapon");
        remove.setOnAction(e -> window.setScene(removeScene));

        update = new Button("Update Weapon");
        update.setOnAction(e -> window.setScene(updateScene));

        combine = new Button("Combine Weapons");
        combine.setOnAction(e -> window.setScene(combineScene));

        exit = new Button("Exit Program");
        exit.setOnAction(e -> closeProgram());

        //Add Database
        Label db = new Label("Please type the directory to a database file.\tExample: C:\\Users\\danie\\Desktop\\weapons.db");
        Label err = new Label("");
        TextField dbText = new TextField();
        Button submit1 = new Button("Submit");
        submit1.setOnAction(e -> {
            weaponListView1.getItems().clear();
            weaponListView2.getItems().clear();
            weaponListView3.getItems().clear();
            weaponListView4.getItems().clear();

            err.setText("");

            String url1 = "jdbc:sqlite:/";
            String url2;
            String fullUrl;
            url2 = dbText.getText();
            fullUrl = url1 + url2;
            dbURL = fullUrl;

            try {
                Connection connection = DriverManager.getConnection(fullUrl);
                dbConnect = connection;

                String sql = "SELECT * FROM Weapons";

                statement = connection.createStatement();
                result1 = statement.executeQuery(sql);
                int c = 1;

                while (result1.next()) {
                    weapon newWeapon = new weapon();

                    newWeapon.setName(result1.getString("Name"));
                    newWeapon.setType(result1.getString("WeaponType"));
                    newWeapon.setDmgType(result1.getString("DamageType"));
                    newWeapon.setPrice(result1.getInt("Price"));
                    newWeapon.setDmgVal(result1.getDouble("DamageValue"));
                    newWeapon.setHitRate(result1.getDouble("HitRate"));

                    weaponListView1.getItems().add(newWeapon);
                    weaponListView2.getItems().add(newWeapon);
                    weaponListView3.getItems().add(newWeapon);
                    weaponListView4.getItems().add(newWeapon);
                    weaponList.add(newWeapon);

                    c++;
                }

                window.setScene(mainMenu);

            } catch (SQLException a) {
                err.setText("Invalid Database Directory");
                throw new RuntimeException(a);
            }
        });

        Button exit2 = new Button("Exit Program");
        exit2.setOnAction(e -> closeProgram());


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
            String name = wpnName.getText();
            String type = ((String) selType.getValue());
            String dmgType = ((String) selDmgType.getValue());
            int price = Integer.parseInt(wpnPrice.getText());
            double dmgVal = Double.parseDouble(wpnDmg.getText());
            double hitRate = Double.parseDouble(wpnHit.getText());
            weapon weapon1 = new weapon();

            if (hitRate < 0 || hitRate > 100) {
                error = true;
                manualResult.setText("Error!: Hit rate entered must be a value between 0 - 100!");
            }

            if (error != true){
                try {
                    System.out.println(dbURL);

                    String sql = "insert into Weapons (Name, WeaponType, DamageType, Price, DamageValue, HitRate) " +
                                "VALUES (\"" + name + "\", \"" + type + "\", \"" + dmgType + "\", "
                                + price + ", " + dmgVal + ", " + hitRate + ")";
                    System.out.println(sql);

                    statement = dbConnect.createStatement();
                    int result1 = statement.executeUpdate(sql);
                    manualResult.setText(wpnName.getText() + " was successfully added!");
                } catch (SQLException ex) {
                    System.out.println("Error occurred");
                    throw new RuntimeException(ex);
                }

                weapon1.setName(name);
                weapon1.setType(type);
                weapon1.setDmgType(dmgType);
                weapon1.setPrice(price);
                weapon1.setDmgVal(dmgVal);
                weapon1.setHitRate(hitRate);
                weaponList.add(weapon1);
                weaponListView1.getItems().add(weapon1);
                weaponListView2.getItems().add(weapon1);
                weaponListView3.getItems().add(weapon1);
                weaponListView4.getItems().add(weapon1);

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
            String weaponName = removeText.getText();
            boolean error = true;

            for (int i = 0; i < weaponList.size(); i++) {
                if (Objects.equals(weaponName, weaponList.get(i).getName())) {
                    error = false;

                    try {
                        String sql = "DELETE FROM Weapons WHERE Name = \"" + weaponName + "\"";
                        System.out.println(sql);

                        statement = dbConnect.createStatement();
                        int result1 = statement.executeUpdate(sql);

                        weaponList.remove(weaponList.get(i));
                        weaponListView1.getItems().remove(i);
                        weaponListView2.getItems().remove(i);
                        weaponListView3.getItems().remove(i);
                        weaponListView4.getItems().remove(i);
                        removeResult.setText(weaponName + " successfully removed.");
                    } catch (SQLException ex) {
                        System.out.println("Error");
                        throw new RuntimeException(ex);
                    }
                }
            }

            if (error){
                removeResult.setText("Error: No weapon with that name was found. \nPlease check spelling/capitalization and try again.");
            }
        });

        goBack4 = new Button("Return");
        goBack4.setOnAction(e -> window.setScene(mainMenu));

        // Update weapon Screen
        VBox updateLayout = new VBox(20);
        Label updateLabel = new Label("\tUPDATE WEAPON");
        Label updateLabel2 = new Label("Please select which weapon you would like to edit");

        ComboBox allWpns = new ComboBox();
        observableList = FXCollections.observableList(weaponList);
        allWpns.setItems(observableList);

        Label updateLabel3 = new Label("Please select the parameter you would like to edit");
        ComboBox allPara = new ComboBox();
        allPara.getItems().add("Name");
        allPara.getItems().add("Weapon Type");
        allPara.getItems().add("Damage Type");
        allPara.getItems().add("Price");
        allPara.getItems().add("Damage Value");
        allPara.getItems().add("Hit Rate");



        Label updateLabel4 = new Label("");
        Button submit2 = new Button("Submit");
        submit2.setOnAction(e -> {
            String selection = ((String) allPara.getValue());

            if(Objects.equals(selection, "Name")){
                updateLabel4.setText("Please enter the new name");
                TextField newThing = new TextField();
                Button nameSubmit = new Button("Submit New Name");
                nameSubmit.setOnAction(a -> {
                    for(int i = 0; i < observableList.size(); i++) {
                        if (allWpns.getValue() == observableList.get(i)) {
                            oldName = observableList.get(i).getName();
                        }
                    }
                    String nameStr = newThing.getText();

                    try {
                        String sql = "Update Weapons set Name = \"" + nameStr + "\" Where Name = \"" + oldName + "\"";
                        System.out.println(sql);

                        statement = dbConnect.createStatement();
                        int result1 = statement.executeUpdate(sql);

                        weaponListView1.refresh();
                        weaponListView2.refresh();
                        weaponListView3.refresh();
                        weaponListView4.refresh();

                    } catch (SQLException ex) {
                        System.out.println("Error");
                        throw new RuntimeException(ex);
                    }
                });
                updateLayout.getChildren().add(7, newThing);
                updateLayout.getChildren().add(8, nameSubmit);
            }
            if(Objects.equals(selection, "Weapon Type")){
                updateLabel4.setText("Please enter the new weapon type");
                ComboBox newThing = new ComboBox();
                newThing.getItems().add("Sword");
                newThing.getItems().add("Gun");
                newThing.getItems().add("Fist");
                Button dmgTypeSubmit = new Button("Submit New Damage Type");
                dmgTypeSubmit.setOnAction(a -> {
                    for(int i = 0; i < observableList.size(); i++) {
                        if (allWpns.getValue() == observableList.get(i)) {
                            oldName = observableList.get(i).getName();
                        }
                    }
                    String dmgTypeStr = ((String) newThing.getValue());

                    try {
                        String sql = "Update Weapons set DamageType = \"" + dmgTypeStr + "\" Where Name = \"" + oldName + "\"";
                        System.out.println(sql);

                        statement = dbConnect.createStatement();
                        int result1 = statement.executeUpdate(sql);

                        weaponListView1.refresh();
                        weaponListView2.refresh();
                        weaponListView3.refresh();
                        weaponListView4.refresh();

                    } catch (SQLException ex) {
                        System.out.println("Error");
                        throw new RuntimeException(ex);
                    }
                });
                updateLayout.getChildren().add(7, newThing);
                updateLayout.getChildren().add(8, dmgTypeSubmit);
            }
            if(Objects.equals(selection, "Damage Type")){
                updateLabel4.setText("Please enter the new damage type");
                ComboBox newThing = new ComboBox();
                newThing.getItems().add("Slash");
                newThing.getItems().add("Strike");
                newThing.getItems().add("Pierce");
                Button dmgTypeSubmit = new Button("Submit New Damage Type");
                dmgTypeSubmit.setOnAction(a -> {
                    for(int i = 0; i < observableList.size(); i++) {
                        if (allWpns.getValue() == observableList.get(i)) {
                            oldName = observableList.get(i).getName();
                        }
                    }
                    String dmgTypeStr = ((String) newThing.getValue());

                    try {
                        String sql = "Update Weapons set DamageType = \"" + dmgTypeStr + "\" Where Name = \"" + oldName + "\"";
                        System.out.println(sql);

                        statement = dbConnect.createStatement();
                        int result1 = statement.executeUpdate(sql);

                        weaponListView1.refresh();
                        weaponListView2.refresh();
                        weaponListView3.refresh();
                        weaponListView4.refresh();

                    } catch (SQLException ex) {
                        System.out.println("Error");
                        throw new RuntimeException(ex);
                    }
                });
                updateLayout.getChildren().add(7, newThing);
                updateLayout.getChildren().add(8, dmgTypeSubmit);
            }
            if(Objects.equals(selection, "Price")){
                updateLabel4.setText("Please enter the new price");
            }
            if(Objects.equals(selection, "Damage Value")){
                updateLabel4.setText("Please enter the new damage value");
            }
            if(Objects.equals(selection, "Hit Rate")){
                updateLabel4.setText("Please enter the new hit rate");
            }
        });

        goBack1 = new Button("Return");
        goBack1.setOnAction(e -> window.setScene(mainMenu));

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

                try {
                    String sql = "insert into Weapons (Name, WeaponType, DamageType, Price, DamageValue, HitRate) " +
                                    "VALUES (\""
                                    + fusedWeapon.getName() + "\", \""
                                    + fusedWeapon.getType() + "\", \""
                                    + fusedWeapon.getDmgType() + "\", "
                                    + fusedWeapon.getPrice() + ", "
                                    + fusedWeapon.getDmgVal() + ", "
                                    + fusedWeapon.getHitRate() + ")";
                    System.out.println(sql);

                    statement = dbConnect.createStatement();
                    int result1 = statement.executeUpdate(sql);
                    combineSuccess.setText("\n\tNEW WEAPON SUCCESSFULLY CREATED");
                } catch (SQLException ex) {
                    System.out.println("Error occurred");
                    throw new RuntimeException(ex);
                }

                weaponList.add(fusedWeapon);
                weaponListView1.getItems().add(fusedWeapon);
                weaponListView2.getItems().add(fusedWeapon);
                weaponListView3.getItems().add(fusedWeapon);
                weaponListView4.getItems().add(fusedWeapon);
            }
            else{
                combineSuccess.setText("Combine failed...");
            }
        });

        goBack5 = new Button("Return");
        goBack5.setOnAction(e -> window.setScene(mainMenu));



        // Add Database Display
        VBox databaseLayout = new VBox(20);
        databaseLayout.getChildren().addAll(db, dbText, err, submit1, exit2);
        addDB = new Scene(databaseLayout, 800, 600);

        // Main Menu Display
        VBox mainMenuLayout = new VBox(20);
        mainMenuLayout.getChildren().addAll(label, newDBbutton, addManualweapon, list, remove, update, combine, exit);
        mainMenu = new Scene(mainMenuLayout, 800, 600);

        // Add Manually Display
        VBox addManualLayout = new VBox(20);
        addManualLayout.getChildren().addAll(addManualLabel, enterName, wpnName, enterType, selType, enterDmgType, selDmgType, enterPrice, wpnPrice, enterDmgVal, wpnDmg, enterHitRate, wpnHit, submit, manualResult, goBack2);
        addManualScene = new Scene(addManualLayout, 800, 700);

        // List Display
        VBox listLayout = new VBox(20);
        listLayout.getChildren().addAll(listLabel, weaponListView1, goBack3);
        listScene = new Scene(listLayout, 800, 600);

        // Remove Display
        VBox removeLayout = new VBox(20);
        removeLayout.getChildren().addAll(removeLabel, weaponListView2, removeLabel2, removeText, removeButton, removeResult, goBack4);
        removeScene = new Scene(removeLayout, 800, 800);

        // Update Display

        updateLayout.getChildren().addAll(updateLabel, updateLabel2, allWpns, updateLabel3, allPara, submit2, updateLabel4, goBack1);
        updateScene = new Scene(updateLayout, 800, 800);

        // Combine Display
        VBox combineLayout = new VBox(20);
        combineLayout.getChildren().addAll(combineLabel, weaponListView3, combineWpn1, wpn1, combineWpn2, wpn2, combineButton, combineResult1, combineResult2, combineSuccess, goBack5);
        combineScene = new Scene(combineLayout, 800, 900);

        window.setScene(addDB);
        window.show();
    }

    /**
     * Daniel Duran
     * CEN 3024 - Software Development 1
     * July 18, 2025
     * closeProgram.java
     * This class simply ends the program when called. It will be used whenever the user selects the 'exit' button.
     */
    public void closeProgram(){
        window.close();
    }
}
