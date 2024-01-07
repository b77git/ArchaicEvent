package com.archaic.archaicevent.Helper;

import com.archaic.archaicevent.ArchaicEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonHelper {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private static final Type PLAYER_DATA_LIST_TYPE = new TypeToken<List<PlayerData>>() {}.getType();
    private static final Type TEAM_DATA_LIST_TYPE = new TypeToken<List<TeamData>>() {}.getType();

    private static <T> void writeDataToFile(List<T> data, File dataFile, Type type) {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(data, type, writer);
            ArchaicEvent.logger.info("Data written successfully to: " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            handleIOException("An error occurred while writing data to the file", e);
        }
    }

    private static <T> void addDataToFile(List<T> existingData, T newData, File dataFile, Type type) {
        if (!existingData.contains(newData)) {
            existingData.add(newData);
            writeDataToFile(existingData, dataFile, type);
        }
    }

    public static void addPlayerDataToFile(PlayerData newData, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);
        addDataToFile(existingData, newData, dataFile, PLAYER_DATA_LIST_TYPE);
    }


    public static List<PlayerData> readExistingPlayerDataFromFile(File dataFile) {
        try (FileReader reader = new FileReader(dataFile)) {
            return gson.fromJson(reader, PLAYER_DATA_LIST_TYPE);
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while reading player data from the file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void updatePlayerDataInFile(PlayerData updatedData, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);
        for (int i = 0; i < existingData.size(); i++) {
            PlayerData existingPlayerData = existingData.get(i);
            if (existingPlayerData.getPlayerUUID().equals(updatedData.getPlayerUUID())) {
                existingData.set(i, updatedData);
                writeDataToFile(existingData, dataFile, PLAYER_DATA_LIST_TYPE);
                return;
            }
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

    public static PlayerData getPlayerDataByName(String playerName, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);

        for (PlayerData playerData : existingData) {
            if (playerData.getPlayerName().equals(playerName)) {
                return playerData;
            }
        }

        return null; // Player data not found
    }

    public static boolean doesPlayerExistByName(String playerName, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);

        for (PlayerData playerData : existingData) {
            if (playerData.getPlayerName().equals(playerName)) {
                return true; // Player found
            }
        }

        return false; // Player not found
    }

    public static void removeTeamDataFromFile(String teamName, File dataFile) {
        // Read existing teams from the file
        List<TeamData> existingTeams = readExistingTeamDataFromFile(dataFile);

        // Find and remove the existing team
        Iterator<TeamData> iterator = existingTeams.iterator();
        while (iterator.hasNext()) {
            TeamData teamData = iterator.next();
            if (teamData.getName().equals(teamName)) {
                // Update each member in the team to no longer be in a team
                for (PlayerData member : teamData.getMembers()) {
                    member.setinTeam(false);
                }
                // Remove the team from the list
                iterator.remove();
            }
        }
        writeDataToFile(existingTeams, dataFile, TEAM_DATA_LIST_TYPE);
    }

    public static List<TeamData> readExistingTeamDataFromFile(File dataFile) {
        try (FileReader reader = new FileReader(dataFile)) {
            // Deserialize the existing data from the file
            return gson.fromJson(reader, TEAM_DATA_LIST_TYPE);
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while reading team data from the file: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list if an error occurs
        }
    }

    public static void addTeamDataToFile(TeamData newTeamData, File dataFile) {
        List<TeamData> existingTeams = readExistingTeamDataFromFile(dataFile);
        addDataToFile(existingTeams, newTeamData, dataFile, TEAM_DATA_LIST_TYPE);
    }

    public static boolean containsTeamData(List<TeamData> dataList, TeamData newTeamData) {
        // Check if the new team data matches any existing entry
        for (TeamData existingTeamData : dataList) {
            if (existingTeamData.getName().equals(newTeamData.getName())) {
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
            if (existingTeamData.getName().equals(updatedTeamData.getName())) {
                // Update the existing team with new data
                existingTeams.set(i, updatedTeamData);

                // Write the updated data back to the file
                writeDataToFile(existingTeams, dataFile, TEAM_DATA_LIST_TYPE);
                return;
            }
        }
    }

    public static TeamData getTeamDataByName(String teamName, File dataFile) {
        List<TeamData> existingTeams = readExistingTeamDataFromFile(dataFile);

        for (TeamData teamData : existingTeams) {
            if (teamData.getName().equals(teamName)) {
                return teamData;
            }
        }

        return null; // Team data not found
    }

    public static TeamData getTeamByMemberName(String memberName, File dataFile) {
        List<TeamData> existingTeams = readExistingTeamDataFromFile(dataFile);

        for (TeamData teamData : existingTeams) {
            for (PlayerData member : teamData.getMembers()) {
                if (member.getPlayerName().equals(memberName)) {
                    return teamData;
                }
            }
        }

        return null; // Player is not a member of any team
    }

    public static boolean doesTeamExistByName(String teamName, File dataFile) {
        List<TeamData> existingTeams = readExistingTeamDataFromFile(dataFile);

        for (TeamData teamData : existingTeams) {
            if (teamData.getName().equals(teamName)) {
                return true; // Team found
            }
        }
        return false; // Team not found
    }

    private static void handleIOException(String message, IOException e) {
        ArchaicEvent.logger.error(message + ": " + e.getMessage());
    }
}
