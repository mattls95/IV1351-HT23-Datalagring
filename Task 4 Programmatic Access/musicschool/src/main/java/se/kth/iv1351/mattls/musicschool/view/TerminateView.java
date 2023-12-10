package se.kth.iv1351.mattls.musicschool.view;

import java.util.List;
import java.util.Scanner;

import se.kth.iv1351.mattls.musicschool.controller.TerminateController;
import se.kth.iv1351.mattls.musicschool.model.Rental;

public class TerminateView {
    private TerminateController ctrl;
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);

    public TerminateView(TerminateController terminateCtrl) {
        this.ctrl = terminateCtrl;
    }

    public void terminateRental() {
        try {
            List<Rental> rentals = null;
            rentals = ctrl.listRentals();
            for (Rental rental : rentals) {
                System.out.println("ID: " + rental.getID()
                        + ", First name : " + rental.getFirstName()
                        + ", Last name : " + rental.getLastName()
                        + ", Instrument id: " + rental.getInstrumentID()
                        + ", Type: " + rental.getInstrumentType()
                        + ", Brand: " + rental.getInstrumentBrand()
                        + ", Price: " + rental.getPrice());
            }

            Integer rentalId = null;
            while (rentalId == null) {
                System.out.println("Enter id of rental to terminate");
                rentalId = parseStringInput(readNextLine());
            }

            ctrl.terminateRental(rentalId);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }

    private Integer parseStringInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Illegal input");
        }
        return null;
    }

}
