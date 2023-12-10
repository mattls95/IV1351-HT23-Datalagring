package se.kth.iv1351.mattls.musicschool.controller;

import java.util.List;

import se.kth.iv1351.mattls.musicschool.integration.MusicSchoolDBException;
import se.kth.iv1351.mattls.musicschool.integration.MusicSchoolListDAO;
import se.kth.iv1351.mattls.musicschool.model.Instrument;
import se.kth.iv1351.mattls.musicschool.model.InstrumentException;

public class ListController {
    private final MusicSchoolListDAO musicSchoolListDb;

    public ListController() throws MusicSchoolDBException {
        musicSchoolListDb = new MusicSchoolListDAO();
    }

    public List<String> getAvailableInstrumentTypes() throws InstrumentException {
        try {
            return musicSchoolListDb.findAllAvailableInstrumentTypes();
        } catch (MusicSchoolDBException e) {
            throw new InstrumentException("Failed to get instrument types.", e);
        }
    }

    public List<Instrument> getAvailableInstruments(String instrumentType) throws InstrumentException {
        try {
            return musicSchoolListDb.findAllInstruments(instrumentType);
        } catch (MusicSchoolDBException e) {
            throw new InstrumentException("Failed to get available instruments.", e);
        }
    }
}
