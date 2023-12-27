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

    public static void addPlayerDataToFile(PlayerData newData, File dataFile) {
        // Read existing entries from the file
        List<PlayerData> existingData = readExistingDataFromFile(dataFile);

        // Check if the new data matches any existing entry
        if (!containsPlayerData(existingData, newData)) {
            // Add the new entry to the existing data
            existingData.add(newData);

            // Write the updated data back to the file
            writeDataToFile(existingData, dataFile);
        }
    }

    private static List<PlayerData> readExistingDataFromFile(File dataFile) {
        try (FileReader reader = new FileReader(dataFile)) {
            // Deserialize the existing data from the file
            return ArchaicEvent.gson.fromJson(reader, PLAYER_DATA_LIST_TYPE);
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while reading from the file: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list if an error occurs
        }
    }

    private static void writeDataToFile(List<PlayerData> data, File dataFile) {
        try (FileWriter writer = new FileWriter(dataFile)) {
            // Serialize and write the updated data to the file
            ArchaicEvent.gson.toJson(data, writer);
            ArchaicEvent.logger.info("Data written successfully to: " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            ArchaicEvent.logger.error("An error occurred while writing to the file: " + e.getMessage());
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
}
