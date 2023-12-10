package se.kth.iv1351.mattls.musicschool.view;

import java.util.Scanner;

import se.kth.iv1351.mattls.musicschool.controller.ListController;
import se.kth.iv1351.mattls.musicschool.controller.RentController;
import se.kth.iv1351.mattls.musicschool.controller.TerminateController;

public class MainMenuView {
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private ListView listView;
    private RentView rentView;
    private TerminateView terminateView;
    private boolean keepReceivingCmds = false;

    public MainMenuView(ListController listCtrl, RentController rentCtrl, TerminateController terminateCtrl) {
        this.listView = new ListView(listCtrl);
        this.rentView = new RentView(rentCtrl);
        this.terminateView = new TerminateView(terminateCtrl);
        System.out.println("Type 'help' for available commands.");
        handleCmds();
    }

    /**
     * 
     */
    public void handleCmds() {
        keepReceivingCmds = true;
        while (keepReceivingCmds) {
            try {
                switch (readNextLine().toLowerCase()) {
                    case "list":
                        listView.listInstruments();
                        break;
                    case "rent":
                        rentView.rentInstrument();
                        break;
                    case "terminate":
                        terminateView.terminateRental();
                        break;
                    case "help":
                        printCommands();
                        break;
                    case "quit":
                        keepReceivingCmds = false;
                        break;
                    default:
                        System.out.println("illegal command");
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void printCommands() {
        System.out.println("1. list");
        System.out.println("2. rent");
        System.out.println("3. terminate");
        System.out.println("4. quit");
    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }
}
