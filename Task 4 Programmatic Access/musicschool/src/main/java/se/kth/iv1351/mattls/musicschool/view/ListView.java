package se.kth.iv1351.mattls.musicschool.view;

import java.util.List;
import java.util.Scanner;

import se.kth.iv1351.mattls.musicschool.controller.ListController;
import se.kth.iv1351.mattls.musicschool.model.Instrument;
import se.kth.iv1351.mattls.musicschool.model.InstrumentException;

public class ListView {
    private ListController ctrl;
    private static final String PROMPT = "> ";
    private boolean keepReceivingCmds = false;
    private final Scanner console = new Scanner(System.in);

    public ListView(ListController ctrl) {
        this.ctrl = ctrl;
    }

    public void listInstruments() {
        keepReceivingCmds = true;
        while (keepReceivingCmds) {
            try {
                List<String> instrumentTypes = null;
                instrumentTypes = ctrl.getAvailableInstrumentTypes();
                for (String instrumentType : instrumentTypes) {
                    System.out.println("Type: " + instrumentType);
                }

                List<Instrument> instrumentsAvailableForRent = null;
                System.out.println("Enter instrument type: ");
                instrumentsAvailableForRent = ctrl.getAvailableInstruments(readNextLine());
                if (!instrumentsAvailableForRent.isEmpty()) {
                    for (Instrument instrument : instrumentsAvailableForRent) {
                        System.out.println(
                                "ID: " + instrument.getInstrumentID()
                                        + " ,Type: " + instrument.getType()
                                        + ", Brand: " + instrument.getBrand()
                                        + ", Price: " + instrument.getPrice());
                    }
                } else {
                    System.out.println("No available instruments.");
                }
                keepReceivingCmds = false;
            } catch (InstrumentException e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
            }

        }
    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }

}
