package se.kth.iv1351.mattls.musicschool.model;

public class Instrument {
    private int instrumentID;
    private String brand;
    private String type;
    private Double price;
    
    public Instrument(int instrumentID,String type, String brand, Double price) {
        this.instrumentID = instrumentID;
        this.type = type;
        this.brand = brand;
        this.price = price;
    }

    public Instrument(int instrumentId) {
        
    }

    public String getType() {
        return this.type;
    }

     public int getInstrumentID() {
        return this.instrumentID;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getBrand() {
        return this.brand;
    }

     /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Instrument: [");
        stringRepresentation.append("Type: ");
        stringRepresentation.append(this.type);
        stringRepresentation.append(", Brand: ");
        stringRepresentation.append(this.brand);
        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}
