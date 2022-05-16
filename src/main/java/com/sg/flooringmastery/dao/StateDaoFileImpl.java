/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

//import com.sg.flooringmastery.dao.PersistenceException;
//import com.sg.flooringmastery.dao.StateDao;
import com.sg.flooringmastery.dto.State;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class StateDaoFileImpl implements StateDao {

    private final String DATA_DIRECTORY;
    private final String STATE_FILE;
    private final String DELIMITER = ",";
    private final Map<String, State> states = new HashMap<>();

    public StateDaoFileImpl() {
        DATA_DIRECTORY = "FileData/Data/";
        STATE_FILE = "States.txt";
    }

    public StateDaoFileImpl(String directory, String fileName) {
        DATA_DIRECTORY = directory;
        STATE_FILE = fileName;
    }

    @Override
    public State getStateByAbbreviation(String abbreviation) throws PersistenceException {
        loadStates();
        return states.get(abbreviation);
    }

    private void loadStates() throws PersistenceException {
        states.clear();
        try (Scanner sc = new Scanner(
                new BufferedReader(
                        new FileReader(DATA_DIRECTORY + STATE_FILE)))) {
            String currentLine = "";
            if (sc.hasNextLine()) {
                currentLine = sc.nextLine();
            }
            State currentState;
            while (sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentState = unmarshallState(currentLine);
                states.put(currentState.getStateAbbreviation(), currentState);
            }
        } catch (IOException e) {
            throw new PersistenceException("Could not load State data into"
                    + " memory.", e);
        }
    }

    private State unmarshallState(String stateAsText) {
        String[] stateTokens = stateAsText.split(DELIMITER);
        return new State(stateTokens[0], stateTokens[1],
                new BigDecimal(stateTokens[2]));
    }

}