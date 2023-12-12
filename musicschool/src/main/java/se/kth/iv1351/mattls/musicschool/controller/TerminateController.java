package se.kth.iv1351.mattls.musicschool.controller;

import java.util.List;

import se.kth.iv1351.mattls.musicschool.integration.MusicSchoolDBException;
import se.kth.iv1351.mattls.musicschool.integration.MusicSchoolTerminateDAO;
import se.kth.iv1351.mattls.musicschool.model.Rental;

public class TerminateController {
    private final MusicSchoolTerminateDAO musicSchoolTerminateDb;

    public TerminateController() throws MusicSchoolDBException {
        musicSchoolTerminateDb = new MusicSchoolTerminateDAO();
    }

    public List<Rental> listRentals() {
        try {
            return musicSchoolTerminateDb.findAllRentals();
        } catch (MusicSchoolDBException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void terminateRental(Integer rentalId) {
        try {
            musicSchoolTerminateDb.updateRentalState(rentalId);
        } catch (MusicSchoolDBException e) {
            System.out.println(e.getMessage());
        }
    }

}
