package com.garume.Garuff.util.api.save;


import com.garume.Garuff.Garuff;
import com.garume.Garuff.Profile;
import com.garume.Garuff.Profiles;
import com.garume.Garuff.command.ChatMessageHandler;
import com.garume.Garuff.command.MessageHandler;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GsonProfiles implements Profiles {

    private final File baseDir;
    private final Gson gson;

    public GsonProfiles(File baseDir, Gson gson) {
        this.baseDir = baseDir;
        this.gson = gson;
    }

    private File getFile(String name) {
        return new File(baseDir, name);
    }

    @Override
    public List<String> getAll() {
        baseDir.mkdirs();
        File[] list = baseDir.listFiles();
        if (list == null) return Collections.emptyList();
        else return Arrays.stream(list).map(File::getName).collect(Collectors.toList());
    }

    @Override
    public Profile load(String name) {
        File dir = getFile(name);
        return new GsonProfile(new File(dir, "profile.json"), new ProfileConfig());
    }


    private class ProfileConfig {
        char prefix = '.';
        String version = Garuff.MOD_VERSION;

        void load(File file) {
            try {
                if (!file.exists()) return;
                String contents = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                ProfileConfig config = gson.fromJson(contents, ProfileConfig.class);
                this.prefix = config.prefix;
                this.version = config.version;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void save(File file) {
            try {
                FileUtils.writeStringToFile(file, gson.toJson(this), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class GsonProfile implements Profile {

        private final File configFile;
        private final ProfileConfig config;

        GsonProfile(File configFile, ProfileConfig config) {
            this.configFile = configFile;
            this.config = config;
        }


        @Override
        public char getPrefix() {
            return config.prefix;
        }

        @Override
        public void setPrefix(char prefix) {
            config.prefix = prefix;
        }

        @Override
        public MessageHandler getMessageHandler() {
            return new ChatMessageHandler();
        }

        @Override
        public void load() {
            config.load(configFile);
        }

        @Override
        public void save() {
            config.save(configFile);
        }
    }
}