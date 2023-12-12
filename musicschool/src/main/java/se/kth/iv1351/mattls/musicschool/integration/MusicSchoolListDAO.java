package se.kth.iv1351.mattls.musicschool.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1351.mattls.musicschool.model.Instrument;

public class MusicSchoolListDAO {
    private PreparedStatement findAllAvailableInstrumentTypesStmt;
    private PreparedStatement findAllAvailableInstrumentsStmt;
    private Connection connection;

    public MusicSchoolListDAO() throws MusicSchoolDBException {
        try {
            connectToBankDB();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new MusicSchoolDBException("Could not connect to datasource.", exception);
        }
    }

    private void connectToBankDB() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Task 4",
                "postgres", "1234");
        connection.setAutoCommit(false);
    }

    public List<String> findAllAvailableInstrumentTypes() throws MusicSchoolDBException {
        String failureMsg = "Could not list instrument types";
        List<String> instrumentTypes = new ArrayList<>();
        ResultSet rs = null;
        try  {
            rs = findAllAvailableInstrumentTypesStmt.executeQuery();
            while (rs.next()) {
                instrumentTypes.add(rs.getString("type"));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(failureMsg, rs);
        }
        return instrumentTypes;
    }

    public List<Instrument> findAllInstruments(String instrumentType) throws MusicSchoolDBException {
        String failureMsg = "Could not list available instruments";
        List<Instrument> instruments = new ArrayList<>();
        ResultSet rs = null;
        try {
            findAllAvailableInstrumentsStmt.setString(1, instrumentType);
            rs = findAllAvailableInstrumentsStmt.executeQuery();
            while (rs.next()) {
                instruments.add(new Instrument(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("brand"),
                        rs.getDouble("price")));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(failureMsg, rs);
        }
        return instruments;
    }

    public void prepareStatements() throws SQLException {
        findAllAvailableInstrumentTypesStmt = connection
                .prepareStatement("SELECT DISTINCT type FROM instrument WHERE is_available = true;");

        findAllAvailableInstrumentsStmt = connection
                .prepareStatement("SELECT * FROM instrument WHERE instrument.type = ?"
                        + " AND is_available = true;");
    }

    private void closeResultSet(String failureMessage, ResultSet result) throws MusicSchoolDBException {
        if (result == null) {
            return;
        }
        try {
            result.close();
        } catch (Exception e) {
            throw new MusicSchoolDBException(failureMessage + " Could not close result set", e);
        }
    }

    private void handleException(String failureMsg, Exception cause) throws MusicSchoolDBException {
        String completeFailureMsg = failureMsg;
        try {
            connection.rollback();
        } catch (SQLException rollbackExc) {
            completeFailureMsg = completeFailureMsg +
                    ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
        }

        if (cause != null) {
            throw new MusicSchoolDBException(failureMsg, cause);
        } else {
            throw new MusicSchoolDBException(failureMsg);
        }
    }
}
