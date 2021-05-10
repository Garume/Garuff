package com.garume.Garuff;

import java.util.List;

public interface Profiles {

    List<String> getAll();

    Profile load(String name);
}
