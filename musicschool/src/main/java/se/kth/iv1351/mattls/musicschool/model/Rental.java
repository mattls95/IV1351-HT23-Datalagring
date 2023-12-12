package se.kth.iv1351.mattls.musicschool.model;

public class Rental {
    private int id;
    private String firstName;
    private String lastName;
    private int instrumentId;
    private String instrumentType;
    private String instrumentBrand;
    private double price;

    public Rental(int id, String firstName, String lastName, int instrumentId, String instrumentType,
            String instrumentBrand,
            double price) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.instrumentId = instrumentId;
        this.instrumentType = instrumentType;
        this.instrumentBrand = instrumentBrand;
        this.price = price;
    }

    public int getID() {
        return this.id;
    }

    public int getInstrumentID() {
        return this.instrumentId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getInstrumentType() {
        return this.instrumentType;
    }

    public String getInstrumentBrand() {
        return this.instrumentBrand;
    }

    public double getPrice() {
        return this.price;
    }
}