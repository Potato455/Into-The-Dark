
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game {
    // fields
    JFrame window;
    Container con;
    JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 75);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);
    JButton startButton, choice1, choice2, choice3, choice4, choice5;
    JTextArea mainTextArea;
    int playerHP, cutNumber=0;
    Random rn = new Random();

    // map is 5 by 5 tiles
    String[][] position = new String[5][5];
    // player position by x and y
    int x, y;
    // booleans to keep track
    boolean beenToChair = false, hasCake = true, hasCanOpener = true, poisoned = false,
            hasKey = false, seenCan = false, trapdoorclosed = true, button1Pressed = false,
            button2Pressed = false, chessLocked = true;


    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ChoiceHandler choiceHandler = new ChoiceHandler();

    public static void main(String[] args) {
        new Game();
    }

    public Game(){
        window = new JFrame();
        window.setSize(800,600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setVisible(true);
        con = window.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600,150);
        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("INTO THE DARK");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300,400,200,100);
        startButtonPanel.setBackground(Color.black);

        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);
        startButton.setFocusPainted(false);


        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        con.add(titleNamePanel);
        con.add(startButtonPanel);
    }

    public void createGameScreen(){

        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100,100,600,250);
        mainTextPanel.setBackground(Color.black);
        con.add(mainTextPanel);

        mainTextArea = new JTextArea("hello there");
        mainTextArea.setBounds(100,100,600,250);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.white);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250,350,300,185);
        choiceButtonPanel.setBackground(Color.black);
        choiceButtonPanel.setLayout(new GridLayout(5,1));
        con.add(choiceButtonPanel);

        choice1 = new JButton("Choice 1");
        choice1.setBackground(Color.black);
        choice1.setForeground(Color.white);
        choice1.setFont(normalFont);
        choiceButtonPanel.add(choice1);
        choice1.setFocusPainted(false);
        choice1.addActionListener(choiceHandler);
        choice1.setActionCommand("c1");

        choice2 = new JButton("Choice 2");
        choice2.setBackground(Color.black);
        choice2.setForeground(Color.white);
        choice2.setFont(normalFont);
        choiceButtonPanel.add(choice2);
        choice2.setFocusPainted(false);
        choice2.addActionListener(choiceHandler);
        choice2.setActionCommand("c2");

        choice3 = new JButton("Choice 3");
        choice3.setBackground(Color.black);
        choice3.setForeground(Color.white);
        choice3.setFont(normalFont);
        choiceButtonPanel.add(choice3);
        choice3.setFocusPainted(false);
        choice3.addActionListener(choiceHandler);
        choice3.setActionCommand("c3");

        choice4 = new JButton("Choice 4");
        choice4.setBackground(Color.black);
        choice4.setForeground(Color.white);
        choice4.setFont(normalFont);
        choiceButtonPanel.add(choice4);
        choice4.setFocusPainted(false);
        choice4.addActionListener(choiceHandler);
        choice4.setActionCommand("c4");

        choice5 = new JButton("Choice 5");
        choice5.setBackground(Color.black);
        choice5.setForeground(Color.white);
        choice5.setFont(normalFont);
        choiceButtonPanel.add(choice5);
        choice5.setFocusPainted(false);
        choice5.addActionListener(choiceHandler);
        choice5.setActionCommand("c5");

        playerPanel = new JPanel();
        playerPanel.setBounds(280,15,300,50);
        playerPanel.setBackground(Color.black);
        playerPanel.setLayout(new GridLayout(1,2));
        con.add(playerPanel);
        hpLabel = new JLabel("HP:");
        hpLabel.setFont(normalFont);
        hpLabel.setForeground(Color.white);
        playerPanel.add(hpLabel);
        hpLabelNumber = new JLabel();
        hpLabelNumber.setFont(normalFont);
        hpLabelNumber.setForeground(Color.white);
        playerPanel.add(hpLabelNumber);

        // map set up
        mapSetup();


        playerSetup();

    }

    public void playerSetup(){
        playerHP = 100;
        hpLabelNumber.setText("" + playerHP);
        // player starts at x:3 y:2 on grid
        x = 2;
        y = 1;

        bed();
    }

    //TODO: death screen
    public void youDied(){
        position[x][y] = "died";
        mainTextArea.setText("My vision seems to be getting blurry...");
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
    }
    public void ExitByDeath(){
        // quit game
        window.setVisible(false);
        window.dispose();
    }

    // decreases HP by getting a random int with max and min
    public void decreaseHP(int max, int min){
        int randomHP = rn.nextInt(max - min + 1)+min;
        playerHP -= randomHP;
        // check if player's dead
        if (playerHP<1) {youDied();}
        else {
            // display new HP
            hpLabelNumber.setText("" + playerHP);
        }
    }
    // increase HP from the cake
    public void increaseHP(int max, int min){
        int randomHP = rn.nextInt(max - min + 1)+min;
        playerHP += randomHP;
        hpLabelNumber.setText("" + playerHP);
    }

    public void mapSetup(){
        // first replace all slots with "nothing"
        for (int x=0;x<5;x++){
            for(int y=0; y<5;y++){
                position[x][y] = "nothing";
            }
        }
        // set up the other slots
        position[2][1] = "bed";
        position[1][0] = "trapdoor";
        position[3][0] = "chest";
        position[4][1] = "gratedWindow";
        position[3][2] = "chair";
        position[4][2] = "button1";
        position[0][3] = "button2";
        position[1][3] = "spike";
        position[3][3] = "table";
        position[2][4] = "corpse";
        position[4][4] = "can";

        // print out array for test
        for (String[] x : position)
        {
            for (String y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
    // set direction text
    public void directionText(){
        choice1.setText("Go North");
        choice2.setText("Go South");
        choice3.setText("Go East");
        choice4.setText("Go West");
    }
    public void bed(){
        position[2][1] = "bed2";
        mainTextArea.setText("\"Ugh... my head.\" I look around but it's pitch \nblack. I stand up from my bed.");
        directionText();
        choice5.setText("Interact");
    }
    public void bed2(){
        position[2][1] = "bed2";
        mainTextArea.setText("Seems like I'm back to the bed I woke up from.");
        directionText();
        choice5.setText("Interact");
    }
    public void interactBed(){
        position[2][1] = "interactBed";
        mainTextArea.setText("Seems like the kind of bed you find in hospitals. \nNothing stands out.");
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
    }
    public void nothing(){
        mainTextArea.setText("I walked for a bit but found nothing...");
        directionText();
        choice5.setText("");
    }
    public void chair(){
        mainTextArea.setText("Thump... I stumbled into a wooden chair.");
        // update been to chair
        beenToChair = true;

        directionText();
        choice5.setText("Sit");
        // lose hp 0-5
        decreaseHP(5, 0);
    }
    public void chair2(){
        position[3][2] = "chair";
        mainTextArea.setText("Where to next...");
        directionText();
        choice5.setText("Sit");
    }
    public void backToChair(){
        mainTextArea.setText("Thump... not again... it's the same bloody wooden \nchair...");
        directionText();
        choice5.setText("Sit");
        // lose hp 0-5
        decreaseHP(5, 0);
    }
    public void sit(){
        position[3][2] = "sit";
        mainTextArea.setText("I sit down... Time passes by as I ponder on my \nexistence with no light in sight...");
        choice1.setText("Stand up");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
    }
    public void spike(){
        mainTextArea.setText("AAAAAAAAAAAAHHHHHH!!!!!\nWHY ARE THERE SPIKES ON THE GROUND!");
        directionText();
        choice5.setText("");
        // lose hp 5-20
        decreaseHP(20, 5);
    }
    public void wallinFront(){
        mainTextArea.setText("There's a wall in front of me.");
    }

    public void table(){
        if (hasCake) {
            mainTextArea.setText("There's a table with a cake on it...");
            directionText();
            choice5.setText("Eat cake");
        }
        else {
            position[3][3] = "table";
            mainTextArea.setText("Back at the table, now what...");
            directionText();
            choice5.setText("");
        }
    }
    public void eatCake(){
        position[3][3] = "eatCake";
        hasCake = false;
        mainTextArea.setText("Mmmmm, what little comfort I can feel\nin this dark reality of mine...");
        // gain hp 10-25
        increaseHP(25,10);
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
    }
    public void corpse(){
        if (hasCanOpener){
            mainTextArea.setText("Ugh... what's this smell... \nThere's a corpse leaned against the wall...");
            directionText();
            choice5.setText("Search");
        }
        else {
            position[2][4] = "corpse";
            mainTextArea.setText("Ugh... the corpse...\nIt gives me a bad feeling...");
            directionText();
            choice5.setText("");
        }
    }
    public void search(){
        position[2][4] = "searching";
        mainTextArea.setText("Oh gosh... I want to puke...\nWhy would he have a can opener though...\nGuess I'll be keeping this.");
        hasCanOpener = false; // corpse no longer has can opener
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
    }
    public void can(){
        position[4][4] = "can";
        // if didn't see can yet
        if (seenCan){
            mainTextArea.setText("Back at the metal can... now what...");
            directionText();
            // if player has key that means he already opened the can
            if (hasKey){
                choice5.setText("");
            }
            else {
                choice5.setText("Open");
            }
        }
        else {
            seenCan = true;
            mainTextArea.setText("Clank... There's a metal can on the ground...\nand it's stuck in place...");
            directionText();
            choice5.setText("Open");
        }

    }
    public void open(){
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        // if can opener hasn't been picked up yet
        if (hasCanOpener) {
            mainTextArea.setText("I fumble around but can't seem to open it...\nIf only I had some sort of tool...");
        }
        // if player has can opener
        else {
            mainTextArea.setText("Oh right, the can opener! I easily opened\nthe can and reached inside to find a key...\nAAARGGHHH SOMETHING BIT ME!");
            // player has key
            hasKey = true;
            // player now poisoned
            poisoned = true;
        }
        position[4][4] = "opening";
    }
    public void button1(){
        position[4][2] = "button1";
        // if pressed opens trapdoor (trapdoorclosed = false;)
        mainTextArea.setText("There's a round button...");
        directionText();
        choice5.setText("Push button");
    }
    public void PressButton1(){
        position[4][2] = "pressing1";
        // trap door now open
        trapdoorclosed = false;
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        //if already pressed
        if (button1Pressed) {
            mainTextArea.setText("I pressed it but nothing seems to be happening.");
        }
        else {
            button1Pressed = true;
            // Player hears things
            mainTextArea.setText("I hear gears turning and something seem to \nhave opened towards the west side.");
        }

    }
    public void button2(){
        position[0][3] = "button2";
        mainTextArea.setText("There's a square button on the wall...");
        directionText();
        choice5.setText("Push button");
    }
    public void PressButton2(){
        position[0][3] = "pressing2";
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        //if already pressed
        if (button2Pressed) {
            // nothing happens
            mainTextArea.setText("I pressed it but nothing seems to be happening.");
        }
        else {
            button2Pressed = true;
            // Player hears things
            mainTextArea.setText("I hear some sort of metallic door sliding");
        }
    }
    public void trapdoor(){
        // if trapdoor is closed
        if (trapdoorclosed){
            mainTextArea.setText("STUMP, STUMP, STUMP, STUMP... \nIt sounds like I'm walking on wood...");
            directionText();
        }
        // if trapdoor open, player falls to his death
        else {
            position[1][0] = "deathByFall";
            mainTextArea.setText("What am I doing here... I just keep wal- \nAAAAAAAAAHHHHHHH!!! I'M FALLING IN \nA H- SPLASH...");
            choice1.setText(">");
            choice2.setText("");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
        }
    }
    public void chest(){
        // if chess is locked
        position[3][0] = "chest";
        if (chessLocked) {
            mainTextArea.setText("There's a chest here, and it requires a key...");
            directionText();
            if (hasKey) {
                choice5.setText("Open");
            }
        }
        // if chess is unlocked
        else {
            mainTextArea.setText("There's nothing else in this chest...");
            directionText();
            choice5.setText("");
        }
    }

    public void openChest(){
        position[3][0] = "openingChest";
        // chess no longer locked
        chessLocked = false;
        mainTextArea.setText("The key fits... After opening, all I found\nwas a small knife...");
        choice1.setText(">");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
    }
    public void gratedWindow(){
        // if both buttons are pressed, wall opens and shows grated window
        if (button1Pressed&&button2Pressed) {
            mainTextArea.setText("A grated window lies before me... Though I \ncan't seem to see what's on the other side.");
            // if player has knife then give option to try cutting the grated window
            if (!chessLocked){
                directionText();
                choice5.setText("Try cutting");
            }
            // otherwise no option
            else {
                directionText();
                choice5.setText("");
            }
        }
        else {
            mainTextArea.setText("There's a suspicious looking metallic wall here...");
            directionText();
            choice5.setText("");
        }
    }
    public void tryCutting(){
        position[4][1] = "tryingToCut";
        if (poisoned){
            decreaseHP(2,0);
        }
        cutNumber += 1;
        mainTextArea.setText("I've been trying to work my way with the\nknife for what seemed like " + cutNumber * 10 + " min...");
        // player can choose to give up after 5 tries
        if (cutNumber<6) {
            choice1.setText("Keep trying");
            choice2.setText("");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
        }
        else {
            choice1.setText("Keep trying");
            choice2.setText("Stop trying");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
        }
    }
    public void stopTrying(){
        position[4][1] = "stoppedTrying";
        mainTextArea.setText("I don't think I'm getting anywhere with this...\nWhat if I just pointed this knife at my throat and...");
        choice1.setText("Push it");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
    }

    public class TitleScreenHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){

            createGameScreen();
        }
    }


    // update text upon arrival at new tile
    public void update(){
        switch (position[x][y]){
            case "bed2": bed2(); break;
            case "nothing": nothing(); break;
            case "chair":
                if (beenToChair) {
                    backToChair();
                }
                else chair();break;
            case "spike": spike(); break;
            case "table": table(); break;
            case "corpse": corpse(); break;
            case "can": can(); break;
            case "button1": button1(); break;
            case "button2": button2(); break;
            case "chest": chest(); break;
            case "trapdoor": trapdoor(); break;
            case "gratedWindow": gratedWindow(); break;
        }
        if (poisoned){
            decreaseHP(2,0);
        }
    }
    public class ChoiceHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String yourChoice = event.getActionCommand();

            switch(position[x][y]){
                case "bed":
                case "bed2":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": interactBed(); break;
                    } System.out.println("where am i: "+position[x][y] + x + y);break;
                case "interactBed":
                    switch(yourChoice){
                        case "c1": bed2(); break;
                    } System.out.println("where am i: "+position[x][y] + x + y);break;
                case "chair":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": sit(); break;
                    } System.out.println("where am i: "+position[x][y] + x + y);break;
                case "sit":
                    switch(yourChoice){
                        case "c1": chair2(); break;
                    } System.out.println("where am i: "+position[x][y] + x + y);break;
                case "spike":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        } break;
                case "table":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": eatCake(); break;
                    } break;
                case "eatCake":
                    switch(yourChoice){
                        case "c1": table(); break;
                    } break;
                case "corpse":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": search(); break;
                    } break;
                case "searching":
                    switch(yourChoice){
                        case "c1": corpse(); break;
                    } break;
                case "can":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": if (!hasKey) {open();} break;
                    } break;
                case "opening":
                    switch(yourChoice){
                        case "c1": can(); break;
                    } break;
                case "deathByFall":
                case "died":
                case "stoppedTrying":
                    switch(yourChoice){
                        case "c1": ExitByDeath(); break;
                    } break;
                case "button1":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": PressButton1(); break;} break;
                case "button2":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": PressButton2(); break;} break;
                case "pressing1":
                    switch(yourChoice){
                        case "c1": button1(); break;
                    } break;
                case "pressing2":
                    switch(yourChoice){
                        case "c1": button2(); break;
                    } break;
                case "openingChest":
                    switch(yourChoice){
                        case "c1": chest(); break;
                    } break;
                case "chest":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": if (chessLocked) {openChest();} break;} break;
                case "gratedWindow":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                        case "c5": if (button1Pressed&&button2Pressed) {tryCutting();} break;} break;
                case "tryingToCut":
                    switch(yourChoice){
                        case "c1": tryCutting(); break;
                        case "c2": if (cutNumber>5) {stopTrying();} break;
                    } break;
                case "nothing":
                    switch(yourChoice){
                        case "c1": if (y == 4) {wallinFront();} else {y++; update();} break;
                        case "c2": if (y == 0) {wallinFront();} else {y--; update();}break;
                        case "c3": if (x == 4) {wallinFront();} else {x++; update();}break;
                        case "c4": if (x == 0) {wallinFront();} else {x--; update();}break;
                    } System.out.println("where am i: "+position[x][y] + x + y);break;
                default: System.out.println("where am i: "+position[x][y] + x + y); break;
            }
        }
    }

}
