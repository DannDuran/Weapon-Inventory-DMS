package com.example.javafx_project;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class weapon {
    private String name;
    private String type;
    private String dmgType;
    private int price;
    private double dmgVal;
    private double hitRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDmgType() {
        return dmgType;
    }

    public void setDmgType(String dmgType) {
        this.dmgType = dmgType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDmgVal() {
        return dmgVal;
    }

    public void setDmgVal(double dmgVal) {
        this.dmgVal = dmgVal;
    }

    public double getHitRate() {
        return hitRate;
    }

    public void setHitRate(double hitRate) {
        this.hitRate = hitRate;
    }

    public static weapon addManual(ArrayList<weapon> weaponList){
        weapon weapon1 = new weapon();

        String name;
        String type;
        String dmgType;
        int price;
        int choice;
        double dmgVal;
        double hitRate;
        boolean error = false;
        boolean confirm = false;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("Enter weapon name: ");
            name = input.nextLine();

            do {
                System.out.println("Enter the weapon's type (Sword/Fist/Gun): ");
                type = input.nextLine();

                if (!Objects.equals(type, "Sword") & !Objects.equals(type, "Fist") & !Objects.equals(type, "Gun") & !Objects.equals(type, "sword") & !Objects.equals(type, "fist") & !Objects.equals(type, "gun")) {
                    error = true;
                    System.out.println("\n\t\tWeapon type must be 'Sword' 'Fist' or 'Gun'.");
                    System.out.println("\t\tPlease check for spelling and try again.\n");
                } else {
                    error = false;
                }
            } while (error);

            do {
                System.out.println("Enter the weapon's damage type (Slash/Strike/Pierce): ");
                dmgType = input.nextLine();

                if (!Objects.equals(dmgType, "Slash") & !Objects.equals(dmgType, "slash") & !Objects.equals(dmgType, "Strike") & !Objects.equals(dmgType, "strike") & !Objects.equals(dmgType, "Pierce") & !Objects.equals(dmgType, "pierce")) {
                    error = true;
                    System.out.println("\n\t\tDamage type must be 'Slash' 'Strike' or 'Pierce'.");
                    System.out.println("\t\tPlease check for spelling and try again.\n");
                } else {
                    error = false;
                }
            } while (error);

            System.out.println("Enter the weapon's price: ");
            price = input.nextInt();
            input.nextLine();

            System.out.println("Enter the weapon's damage value: ");
            dmgVal = input.nextDouble();

            do {
                System.out.println("Enter the weapon's hit rate (0% - 100%): ");
                hitRate = input.nextDouble();

                if (hitRate < 0 || hitRate > 100) {
                    error = true;
                    System.out.println("\t\tWeapon's hit rate must be a value between 0 and 100");
                    System.out.println("\t\tPlease try again.\n");
                } else {
                    error = false;
                }
            } while (error);

            System.out.println("\n\tWeapon name: " + name);
            System.out.println("\tWeapon type: " + type);
            System.out.println("\tWeapon damage type: " + dmgType);
            System.out.println("\tWeapon price: $" + price);
            System.out.println("\tWeapon damage value: " + dmgVal);
            System.out.println("\tWeapon hit rate: " + hitRate + "%");

            System.out.println("\n\tDoes this look correct?");
            System.out.println("\t1. Yes, add this weapon.");
            System.out.println("\t2. No, let me re-enter my weapon.");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    confirm = true;
                    break;
                case 2:
                    confirm = false;
                    break;
                default:
                    error = true;
                    System.out.println("Error: Please select 1 or 2.");
            }
        } while(!confirm);

        weapon1.setName(name);
        weapon1.setType(type);
        weapon1.setDmgType(dmgType);
        weapon1.setPrice(price);
        weapon1.setDmgVal(dmgVal);
        weapon1.setHitRate(hitRate);

        return weapon1;
    }

    public static void removeWeapon(ArrayList<weapon> weaponList) {
        Scanner input = new Scanner(System.in);
        String weaponName;
        int choice;
        boolean error = false;
        boolean confirm = false;

        do {
            do {
                System.out.println("\nEnter the name of the weapon you wish to remove: ");
                System.out.println("\t\t(Names are case sensitive)");
                weaponName = input.nextLine();

                for (int i = 0; i < weaponList.size(); i++) {
                    if (Objects.equals(weaponName, weaponList.get(i).getName())) {
                        System.out.println("\n\tWeapon name: " + weaponList.get(i).getName());
                        System.out.println("\tWeapon type: " + weaponList.get(i).getType());
                        System.out.println("\tWeapon damage type: " + weaponList.get(i).getDmgType());
                        System.out.println("\tWeapon price: $" + weaponList.get(i).getPrice());
                        System.out.println("\tWeapon damage value: " + weaponList.get(i).getDmgVal());
                        System.out.println("\tWeapon hit rate: " + weaponList.get(i).getHitRate() + "%");

                        System.out.println("\n\tDoes this look correct?");
                        System.out.println("\t1. Yes, remove this weapon.");
                        System.out.println("\t2. No, let me re-enter the weapon name.");
                        choice = input.nextInt();
                        input.nextLine();

                        switch (choice) {
                            case 1:
                                confirm = true;
                                error = false;

                                weaponList.remove(i);
                                System.out.println(weaponName + " was removed.");
                                break;
                            case 2:
                                confirm = false;
                                break;
                            default:
                                error = true;
                                System.out.println("Error: Please select 1 or 2.");
                        }

                    } else {
                        error = true;
                    }
                }

                if (error) {
                    System.out.println("\tNo weapon with that name was found.");
                    System.out.println("\tPlease check spelling/capitalization and try again.");
                }
            } while (error);
        }while(!confirm);
    }

    public static void listWeapons(ArrayList<weapon> weaponList) {
        for (int i = 0; i < weaponList.size(); i++) {
            System.out.println("\t\tWeapon " + (i + 1) + weaponList.get(i).toString() + "\n");
        }
    }

    public static weapon combineWeapon(ArrayList<weapon> weaponList){
        weapon wpn1 = new weapon();
        weapon wpn2 = new weapon();
        weapon fusedWeapon = new weapon();
        String wpnName1;
        String wpnName2;
        Scanner input = new Scanner(System.in);
        int choice;
        boolean error = false;
        boolean confirm = false;

        do {
            do {
                System.out.println("\nEnter the name of the first weapon you wish to use: ");
                System.out.println("\t\t(Names are case sensitive)");
                wpnName1 = input.nextLine();

                for (int i = 0; i < weaponList.size(); i++) {
                    if (Objects.equals(wpnName1, weaponList.get(i).getName())) {

                        System.out.println(weaponList.get(i).toString());
                        System.out.println("\n\tDoes this look correct?");
                        System.out.println("\t1. Yes, select this weapon.");
                        System.out.println("\t2. No, let me re-enter the weapon name.");
                        choice = input.nextInt();
                        input.nextLine();

                        switch (choice) {
                            case 1:
                                confirm = true;
                                error = false;

                                wpn1 = weaponList.get(i);
                                System.out.println(wpnName1 + " was selected.");
                                break;
                            case 2:
                                confirm = false;
                                break;
                            default:
                                error = true;
                                System.out.println("Error: Please select 1 or 2.");
                        }
                    } else {
                        if(i == weaponList.size()) {
                            error = true;
                            System.out.println("\tNo weapon with that name was found.");
                            System.out.println("\tPlease check spelling/capitalization and try again.");
                        }
                    }
                }
            } while (error);
        }while(!confirm);

        do {
            do {
                System.out.println("\nEnter the name of the second weapon you wish to use: ");
                System.out.println("\t\t(Names are case sensitive)");
                wpnName2 = input.nextLine();

                for (int i = 0; i < weaponList.size(); i++) {
                    if (Objects.equals(wpnName2, weaponList.get(i).getName())) {
                        System.out.println(weaponList.get(i).toString());

                        System.out.println("\n\tDoes this look correct?");
                        System.out.println("\t1. Yes, select this weapon.");
                        System.out.println("\t2. No, let me re-enter the weapon name.");
                        choice = input.nextInt();
                        input.nextLine();

                        switch (choice) {
                            case 1:
                                confirm = true;
                                error = false;

                                wpn2 = weaponList.get(i);
                                System.out.println(wpnName2 + " was selected.");
                                break;
                            case 2:
                                confirm = false;
                                break;
                            default:
                                error = true;
                                System.out.println("Error: Please select 1 or 2.");
                        }
                    } else {
                        if(i == weaponList.size()) {
                            error = true;
                            System.out.println("\tNo weapon with that name was found.");
                            System.out.println("\tPlease check spelling/capitalization and try again.");
                        }
                    }
                }


            } while (error);
        }while(!confirm);

        fusedWeapon.setName(wpn1.getName() + " + " + wpn2.getName());
        fusedWeapon.setDmgType(wpn1.getDmgType());
        fusedWeapon.setType(wpn1.getType());
        fusedWeapon.setPrice(wpn1.getPrice() + wpn2.getPrice());
        fusedWeapon.setDmgVal((wpn1.getDmgVal() + wpn2.getDmgVal()) / 2);
        fusedWeapon.setHitRate((wpn1.getHitRate() + wpn2.getHitRate()) / 2);

        System.out.println("\n\t\tNEW WEAPON SUCCESSFULLY CREATED");
        return fusedWeapon;
    }
    public static void editWeapon(ArrayList<weapon> weaponList){
        Scanner input = new Scanner(System.in);
        String weaponName;
        int choice;
        boolean error = false;
        boolean confirm = true;

        String newName;
        String newType;
        String newDmgType;
        int newPrice;
        double newDmgVal;
        double newHitRate;

        do {
            do {
                error = false;
                System.out.println("\nEnter the name of the weapon you wish to edit: ");
                System.out.println("\t\t(Names are case sensitive)");
                weaponName = input.nextLine();

                for (int i = 0; i < weaponList.size(); i++) {
                    if (Objects.equals(weaponName, weaponList.get(i).getName())) {
                        do {
                            error = false;

                            System.out.println("\n\tWeapon name: " + weaponList.get(i).getName());
                            System.out.println("\tWeapon type: " + weaponList.get(i).getType());
                            System.out.println("\tWeapon damage type: " + weaponList.get(i).getDmgType());
                            System.out.println("\tWeapon price: $" + weaponList.get(i).getPrice());
                            System.out.println("\tWeapon damage value: " + weaponList.get(i).getDmgVal());
                            System.out.println("\tWeapon hit rate: " + weaponList.get(i).getHitRate() + "%");

                            System.out.println("\n\tDoes this look correct?");
                            System.out.println("\t1. Yes, edit this weapon.");
                            System.out.println("\t2. No, let me re-enter the weapon name.");
                            choice = input.nextInt();
                            input.nextLine();


                            switch (choice) {
                                case 1:
                                    do {
                                        confirm = true;
                                        error = false;

                                        System.out.println("Select what parameter you would like to edit.");
                                        System.out.println("1. Weapon Name");
                                        System.out.println("2. Weapon Type");
                                        System.out.println("3. Weapon Damage Type");
                                        System.out.println("4. Weapon Price");
                                        System.out.println("5. Weapon Damage Value");
                                        System.out.println("6. Weapon Hit Rate");
                                        choice = input.nextInt();
                                        input.nextLine();


                                        switch (choice) {
                                            case 1:
                                                error = false;
                                                System.out.println("Enter the weapon's new name: ");
                                                newName = input.nextLine();

                                                weaponList.get(i).setName(newName);
                                                System.out.println("New name: " + newName);
                                                break;
                                            case 2:
                                                error = false;
                                                do {
                                                    System.out.println("Enter the weapon's new type (Sword/Fist/Gun): ");
                                                    newType = input.nextLine();

                                                    if (!Objects.equals(newType, "Sword") & !Objects.equals(newType, "Fist") & !Objects.equals(newType, "Gun") & !Objects.equals(newType, "sword") & !Objects.equals(newType, "fist") & !Objects.equals(newType, "gun")) {
                                                        error = true;
                                                        System.out.println("\n\t\tWeapon type must be 'Sword' 'Fist' or 'Gun'.");
                                                        System.out.println("\t\tPlease check for spelling and try again.\n");
                                                    } else {
                                                        error = false;
                                                    }
                                                } while (error);

                                                System.out.println("New weapon type: " + newType);
                                                break;
                                            case 3:
                                                do {
                                                    System.out.println("Enter the weapon's new damage type (Slash/Strike/Pierce): ");
                                                    newDmgType = input.nextLine();

                                                    if (!Objects.equals(newDmgType, "Slash") & !Objects.equals(newDmgType, "slash") & !Objects.equals(newDmgType, "Strike") & !Objects.equals(newDmgType, "strike") & !Objects.equals(newDmgType, "Pierce") & !Objects.equals(newDmgType, "pierce")) {
                                                        error = true;
                                                        System.out.println("\n\t\tDamage type must be 'Slash' 'Strike' or 'Pierce'.");
                                                        System.out.println("\t\tPlease check for spelling and try again.\n");
                                                    } else {
                                                        error = false;
                                                    }
                                                } while (error);

                                                System.out.println("New damage type: " + newDmgType);
                                                break;
                                            case 4:
                                                System.out.println("Enter the weapon's new price: ");
                                                newPrice = input.nextInt();
                                                input.nextLine();
                                                break;
                                            case 5:
                                                System.out.println("Enter the weapon's new damage value: ");
                                                newDmgVal = input.nextDouble();
                                                break;
                                            case 6:
                                                do {
                                                    System.out.println("Enter the weapon's new hit rate (0% - 100%): ");
                                                    newHitRate = input.nextDouble();
                                                    input.nextLine();

                                                    if (newHitRate < 0 || newHitRate > 100) {
                                                        error = true;
                                                        System.out.println("\t\tWeapon's hit rate must be a value between 0 and 100");
                                                        System.out.println("\t\tPlease try again.\n");
                                                    } else {
                                                        error = false;
                                                    }
                                                } while (error);
                                                break;
                                            default:
                                                error = true;
                                                System.out.println("Error: Please select 1-6.");
                                        }
                                        break;
                                    } while (error);
                                case 2:
                                    error = false;
                                    confirm = false;
                                    break;
                                default:
                                    error = true;
                                    System.out.println("Error: Please select 1 or 2.");
                            }
                        }while(error);
                    } else {
                        if(i == weaponList.size()) {
                            error = true;
                            System.out.println("\tNo weapon with that name was found.");
                            System.out.println("\tPlease check spelling/capitalization and try again.");
                        }
                    }
                }
            } while (error);
        }while(!confirm);
    }
    
    public String toString() {
        return
                "\t\nName: " + name +
                "\t\nType: " + type +
                "\t\nDamage Type: " + dmgType +
                "\t\nPrice: " + price +
                "\t\nDamage Value: " + dmgVal +
                "\t\nHit Rate: " + hitRate + '%';
    }
}
