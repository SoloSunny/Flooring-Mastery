package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author solomon
 */
public class StateDaoFileImplTest {

    private StateDao testDao;

    @BeforeEach
    public void setUp() throws PersistenceException {
        String directory = "FileData/TestFiles/TestData/";
        String testFile = "teststate.txt";
        try (PrintWriter out = new PrintWriter(
                new FileWriter(directory + testFile))) {
            out.println("State,StateName,TaxRate");
            out.println("TX,Texas,4.45");
            out.println("WA,Washington,9.25");
            out.flush();
        } catch (IOException e) {
            throw new PersistenceException("Could not set up test inventory"
                    + "file.", e);
        }
        testDao = new StateDaoFileImpl(directory, testFile);
    }

    @Test
    public void getStateByAbbreviationTest() throws PersistenceException {
        State texas = new State("TX", "Texas", new BigDecimal("4.45"));
        State washington = new State("WA", "Washington", new BigDecimal("9.25"));
        assertNull(testDao.getStateByAbbreviation("KY"), "Kentucky (KY) is not "
                + "in the states list.");
        assertEquals(texas, testDao.getStateByAbbreviation("TX"), "Returned "
                + "State should be Texas.");
        assertEquals(washington, testDao.getStateByAbbreviation("WA"), "Returned"
                + " State should be Washington.");
    }

}