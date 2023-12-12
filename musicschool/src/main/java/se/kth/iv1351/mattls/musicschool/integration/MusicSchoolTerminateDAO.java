package se.kth.iv1351.mattls.musicschool.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1351.mattls.musicschool.model.Rental;

public class MusicSchoolTerminateDAO {
    private Connection connection;
    private PreparedStatement findCurrentRentalsStmt;
    private PreparedStatement selectRentalForUpdateStmt;
    private PreparedStatement updateRentalStateStmt;
    private PreparedStatement selectInstrumentForUpdate;
    private PreparedStatement updateInstrumentAvailabilityStmt;

    public MusicSchoolTerminateDAO() throws MusicSchoolDBException {
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

    public List<Rental> findAllRentals() throws MusicSchoolDBException {
        String failureMsg = "Could not list rentals";
        List<Rental> rentals = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = findCurrentRentalsStmt.executeQuery();
            while (rs.next()) {
                rentals.add(new Rental(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("instrument_id"),
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
        return rentals;
    }

    public void updateRentalState(int rentalId) throws MusicSchoolDBException {
        String failureMsg = "Could not get student rental status";
        ResultSet rs = null;
        ResultSet rsInstrumentSelect = null;
        try {
            selectRentalForUpdateStmt.setInt(1, rentalId);
            rs = selectRentalForUpdateStmt.executeQuery();

            updateRentalStateStmt.setInt(1, rentalId);
            int updatedRows = updateRentalStateStmt.executeUpdate();

            selectInstrumentForUpdate.setInt(1, updatedRows);
            rsInstrumentSelect = selectInstrumentForUpdate.executeQuery();

            updateInstrumentAvailabilityStmt.setInt(1, rentalId);
            int updatedInstrumentRows = updateInstrumentAvailabilityStmt.executeUpdate();

            if (updatedRows != 1 || !rsInstrumentSelect.next() || !rs.next() || updatedInstrumentRows != 1) {
                handleException(failureMsg, null);
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(failureMsg, rs);
        }
    }

    public void prepareStatements() throws SQLException {
        findCurrentRentalsStmt = connection
                .prepareStatement("SELECT rental.id, first_name,last_name,instrument_id,type,brand,price FROM rental"
                        + " INNER JOIN student ON student.id = rental.student_id"
                        + " INNER JOIN instrument On instrument.id = rental.instrument_id"
                        + " INNER JOIN person ON person.id = student.person_id"
                        + " WHERE rental.time_terminated IS NULL"
                        + " ORDER BY rental.id;");

        selectRentalForUpdateStmt = connection.prepareStatement("SELECT id FROM rental WHERE id = ? FOR UPDATE;");

        updateRentalStateStmt = connection.prepareStatement("UPDATE rental SET time_terminated = now() WHERE id = ?;");

        selectInstrumentForUpdate = connection.prepareStatement(
                "SELECT id FROM instrument WHERE id = (SELECT instrument_id FROM rental WHERE id = ?) FOR UPDATE;");
        updateInstrumentAvailabilityStmt = connection
                .prepareStatement(
                        "UPDATE instrument SET is_available = true WHERE id = (SELECT instrument_id FROM rental WHERE id = ?);");
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
