package com.garume.Garuff.command.parser;



import com.garume.Garuff.command.ParseException;
import com.garume.Garuff.module.Module;
import com.garume.Garuff.module.ModuleManager;

import java.util.Stack;

public class ModuleParser implements TypeParser<Module> {
    @Override
    public Module parse(int index, Stack<String> args) throws ParseException {
        if (args.isEmpty())
            throw new ParseException("Missing module name/id at index " + index);
        String name = args.pop();
        for (Module module : ModuleManager.getModules()) {
            if (!module.getName().equalsIgnoreCase(name)
                //&& !module.getId().equalsIgnoreCase(name)
                ) continue;
            return module;
        }
        throw new ParseException("A module named " + name + " was not found");
    }

    @Override
    public String getToken() {
        return "module";
    }

    @Override
    public Class<Module> getType() {
        return Module.class;
    }
}
