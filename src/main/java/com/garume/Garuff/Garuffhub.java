package com.garume.Garuff;


public class Garuffhub {

    private static Profile profile;
    private static Profiles profiles;

    public static Profile getProfile() {
        return profile;
    }

    public static void setProfile(Profile profile) {
        Garuffhub.profile = profile;
    }

    public static Profiles getProfiles() {
        return profiles;
    }

    public static void setProfiles(Profiles profiles) {
        Garuffhub.profiles = profiles;
    }
}