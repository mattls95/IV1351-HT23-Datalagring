package se.kth.iv1351.mattls.musicschool.controller;

import java.util.List;

import se.kth.iv1351.mattls.musicschool.integration.MusicSchoolDBException;
import se.kth.iv1351.mattls.musicschool.integration.MusicSchoolRentDAO;
import se.kth.iv1351.mattls.musicschool.model.Instrument;
import se.kth.iv1351.mattls.musicschool.model.RentalException;
import se.kth.iv1351.mattls.musicschool.model.Student;

public class RentController {
    private final MusicSchoolRentDAO musicSchoolRentDb;

    public RentController() throws MusicSchoolDBException {
        musicSchoolRentDb = new MusicSchoolRentDAO();
    }

    public List<Instrument> getAvailableInstrument() throws RentalException{
        String failureMessage = "Failed to retrieve instruments" ;
        try {
            return musicSchoolRentDb.findAllInstruments();
        } catch (MusicSchoolDBException e) {
            throw new RentalException(failureMessage);
        }
    }

    public List<Student> getAllStudents() throws RentalException{
        String failureMessage = "Failed to retrieve students" ;
        try {
            return musicSchoolRentDb.findAllStudents();
        } catch (MusicSchoolDBException e) {
            throw new RentalException(failureMessage);
        }
    }

    public void createRental(Integer studentId, Integer instrumentId) throws RentalException {
        String failureMessage = "Rental failed for student " + String.valueOf(studentId) ;
        try {
            musicSchoolRentDb.createRental(studentId, instrumentId);
        } catch (MusicSchoolDBException e) {
           throw new RentalException(failureMessage);
        }
    }
}
