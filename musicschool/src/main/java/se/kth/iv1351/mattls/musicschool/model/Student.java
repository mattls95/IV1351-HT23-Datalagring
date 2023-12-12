package se.kth.iv1351.mattls.musicschool.model;

public class Student {
    private int studentID;
    private String firstName;
    private String lastName;
    private String ssn;
    private boolean isElgibleToRent;

    public Student(int studentID, String ssn, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }

    public Student() {
    }

    public Student(boolean status) {
        this.isElgibleToRent = status;
    }

    public int getId() {
        return this.studentID;
    }
    public boolean getisElgibleToRent() {
        return this.isElgibleToRent;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getSSN() {
        return this.ssn;
    }

    public void setRentalStatus(boolean status) {
        this.isElgibleToRent = status;
}
}
