package se.kth.iv1351.mattls.musicschool.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1351.mattls.musicschool.model.Instrument;
import se.kth.iv1351.mattls.musicschool.model.Student;

public class MusicSchoolRentDAO {

    private Connection connection;
    private PreparedStatement findAllAvailableInstruments;
    private PreparedStatement findAllStudentsStmt;
    private PreparedStatement selectInstrumentForUpdate;
    private PreparedStatement updateInstrumentAvailabilityStmt;
    private PreparedStatement createRentalStmt;
    private PreparedStatement findStudentEligibilityStatusStmt;

    public MusicSchoolRentDAO() throws MusicSchoolDBException {
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

    public List<Student> findAllStudents() throws MusicSchoolDBException {
        String failureMsg = "Could not list students";
        List<Student> students = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = findAllStudentsStmt.executeQuery();
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"),
                        rs.getString("person_number"),
                        rs.getString("first_name"),
                        rs.getString("last_name")));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(rs);
        }
        return students;
    }

    public List<Instrument> findAllInstruments() throws MusicSchoolDBException {
        String failureMsg = "Could not list available instruments";
        List<Instrument> instruments = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = findAllAvailableInstruments.executeQuery();
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
            closeResultSet(rs);
        }
        return instruments;
    }

    public void createRental(Integer studentId, Integer instrumentId) throws MusicSchoolDBException {
        String failureMsg = "Rental failed for student ID " + studentId;
        ResultSet resultSetStudentElgibility = null;
        ResultSet rs = null;
        try {
            findStudentEligibilityStatusStmt.setInt(1, studentId);
            resultSetStudentElgibility = findStudentEligibilityStatusStmt.executeQuery();
            if (resultSetStudentElgibility.next()) {
                throw new MusicSchoolDBException("Student is not elgible to rent", null);
            }

            selectInstrumentForUpdate.setInt(1, instrumentId);
            rs = selectInstrumentForUpdate.executeQuery();

            updateInstrumentAvailabilityStmt.setInt(1, instrumentId);
            int updatedRowsForUpdateOnInstrument = updateInstrumentAvailabilityStmt.executeUpdate();

            createRentalStmt.setInt(1, instrumentId);
            createRentalStmt.setInt(2, studentId);
            int updatedRowsForInsertIntoRental = createRentalStmt.executeUpdate();

            if (updatedRowsForUpdateOnInstrument != 1 || updatedRowsForInsertIntoRental != 1 || !rs.next()) {
                handleException(failureMsg, null);
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(resultSetStudentElgibility);
            closeResultSet(rs);
        }
    }

    public void prepareStatements() throws SQLException {
        findAllAvailableInstruments = connection
                .prepareStatement("SELECT * FROM instrument WHERE is_available = true ORDER By id;");
        findAllStudentsStmt = connection
                .prepareStatement("SELECT student.id,person_number,first_name,last_name FROM student"
                        + " INNER JOIN person ON person.id = student.person_id;");
        findStudentEligibilityStatusStmt = connection.prepareStatement("SELECT student_id FROM rental"
                + " WHERE student_id = ?"
                + " AND time_terminated IS null"
                + " GROUP BY student_id"
                + " HAVING COUNT(student_id) >= 2;");
        selectInstrumentForUpdate = connection.prepareStatement("SELECT id FROM instrument WHERE id = ? FOR UPDATE;");
        updateInstrumentAvailabilityStmt = connection
                .prepareStatement("UPDATE instrument SET is_available = false WHERE id = ?;");
        createRentalStmt = connection.prepareStatement("INSERT INTO rental (instrument_id, student_id, time_rented)"
                + "VALUES(?, ?, now());");
    }

    private void closeResultSet(ResultSet result) throws MusicSchoolDBException {
        if (result == null) {
            return;
        }
        try {
            result.close();
        } catch (Exception e) {
            throw new MusicSchoolDBException("Could not close result set", e);
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
