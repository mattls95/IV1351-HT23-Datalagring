package se.kth.iv1351.mattls.musicschool.view;

import java.util.List;
import java.util.Scanner;

import se.kth.iv1351.mattls.musicschool.controller.RentController;
import se.kth.iv1351.mattls.musicschool.model.Instrument;
import se.kth.iv1351.mattls.musicschool.model.RentalException;
import se.kth.iv1351.mattls.musicschool.model.Student;

public class RentView {
    private RentController ctrl;
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);

    public RentView(RentController ctrl) {
        this.ctrl = ctrl;
    }

    public void rentInstrument() {
        try {
            List<Student> students = null;
            students = ctrl.getAllStudents();
            for (Student student : students) {
                System.out.println("ID: " + student.getId()
                        + ", SSN: " + student.getSSN()
                        + ", First name: " + student.getFirstName()
                        + ", Last name: " + student.getLastName());
            }
            Integer studentId = null;
            while (studentId == null) {
                System.out.println("Enter id of student to rent");
                studentId = parseStringInput(readNextLine());
            }
            List<Instrument> instrumentsAvailableForRent = null;
            instrumentsAvailableForRent = ctrl.getAvailableInstrument();
            for (Instrument instrument : instrumentsAvailableForRent) {
                System.out.println(
                        "ID: " + instrument.getInstrumentID()
                                + " ,Type: " + instrument.getType()
                                + ", Brand: " + instrument.getBrand()
                                + ", Price: " + instrument.getPrice());
            }
            Integer instrumentId = null;
            while (instrumentId == null) {
                System.out.println("Enter id of instrument to rent");
                instrumentId = parseStringInput(readNextLine());
            }
            ctrl.createRental(studentId, instrumentId);
        } catch (RentalException e) {
            System.out.println("Operation failed");
            System.out.println(e.getMessage());
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
