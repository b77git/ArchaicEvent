package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private static final Type PLAYER_DATA_LIST_TYPE = new TypeToken<List<PlayerData>>() {}.getType();
    private static final Type TEAM_DATA_LIST_TYPE = new TypeToken<List<TeamData>>() {}.getType();

    public static void addPlayerDataToFile(PlayerData newData, File dataFile) {
        // Read existing entries from the file
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);

        // Check if the new data matches any existing entry
        if (!containsPlayerData(existingData, newData)) {
            // Add the new entry to the existing data
            existingData.add(newData);

            // Write the updated data back to the file
            writePlayerDataToFile(existingData, dataFile);
        }
    }

    public static List<PlayerData> readExistingPlayerDataFromFile(File dataFile) {
        try (FileReader reader = new FileReader(dataFile)) {
            // Deserialize the existing data from the file
            return ArchaicEvent.gson.fromJson(reader, PLAYER_DATA_LIST_TYPE);
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while reading player data from the file: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list if an error occurs
        }
    }

    private static void writePlayerDataToFile(List<PlayerData> data, File dataFile) {
        try (FileWriter writer = new FileWriter(dataFile)) {
            // Serialize and write the updated data to the file
            ArchaicEvent.gson.toJson(data, writer);
            ArchaicEvent.logger.info("Player data written successfully to: " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while writing player data to the file: " + e.getMessage());
        }
    }

    private static boolean containsPlayerData(List<PlayerData> dataList, PlayerData newData) {
        // Check if the new data matches any existing entry
        for (PlayerData existingData : dataList) {
            if (existingData.getPlayerName().equals(newData.getPlayerName()) &&
                    existingData.getPlayerUUID().equals(newData.getPlayerUUID())) {
                return true; // Match found
            }
        }
        return false; // No match found
    }

    public static void updatePlayerDataInFile(PlayerData updatedData, File dataFile) {
        // Read existing entries from the file
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);

        // Find and update the existing entry
        for (int i = 0; i < existingData.size(); i++) {
            PlayerData existingPlayerData = existingData.get(i);
            if (existingPlayerData.getPlayerUUID().equals(updatedData.getPlayerUUID())) {
                // Update the existing entry with new data
                existingData.set(i, updatedData);

                // Write the updated data back to the file
                writePlayerDataToFile(existingData, dataFile);
                return; // Exit the method after updating
            }
        }
    }

    public static void addTeamDataToFile(TeamData newTeamData, File dataFile) {
        // Read existing teams from the file
        List<TeamData> existingTeams = readExistingTeamDataFromFile(dataFile);

        // Check if the new team data matches any existing entry
        if (!containsTeamData(existingTeams, newTeamData)) {
            // Add the new entry to the existing data
            existingTeams.add(newTeamData);

            // Write the updated data back to the file
            writeTeamDataToFile(existingTeams, dataFile);
        }
    }

    public static List<TeamData> readExistingTeamDataFromFile(File dataFile) {
        try (FileReader reader = new FileReader(dataFile)) {
            // Deserialize the existing data from the file
            return ArchaicEvent.gson.fromJson(reader, TEAM_DATA_LIST_TYPE);
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while reading team data from the file: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list if an error occurs
        }
    }

    private static void writeTeamDataToFile(List<TeamData> data, File dataFile) {
        try (FileWriter writer = new FileWriter(dataFile)) {
            // Serialize and write the updated data to the file
            ArchaicEvent.gson.toJson(data, writer);
            ArchaicEvent.logger.info("Team data written successfully to: " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while writing team data to the file: " + e.getMessage());
        }
    }

    private static boolean containsTeamData(List<TeamData> dataList, TeamData newTeamData) {
        // Check if the new team data matches any existing entry
        for (TeamData existingTeamData : dataList) {
            if (existingTeamData.getTeamName().equals(newTeamData.getTeamName())) {
                return true; // Match found
            }
        }
        return false; // No match found
    }

    public static void updateTeamDataInFile(TeamData updatedTeamData, File dataFile) {
        // Read existing teams from the file
        List<TeamData> existingTeams = readExistingTeamDataFromFile(dataFile);

        // Find and update the existing team
        for (int i = 0; i < existingTeams.size(); i++) {
            TeamData existingTeamData = existingTeams.get(i);
            if (existingTeamData.getTeamName().equals(updatedTeamData.getTeamName())) {
                // Update the existing team with new data
                existingTeams.set(i, updatedTeamData);

                // Write the updated data back to the file
                writeTeamDataToFile(existingTeams, dataFile);
                return; // Exit the method after updating
            }
        }
    }
}
