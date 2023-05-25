package pl.shadxw.core.models;

import lombok.Getter;

public abstract class ConfigFile {

    @Getter private final String path;
    private final boolean canCreateIfNotExists;

    protected ConfigFile(String path, boolean canCreateIfNotExists){
        this.path = path;
        this.canCreateIfNotExists = canCreateIfNotExists;
    }

    protected abstract void forceCreate(String templateFileName);

    protected abstract void create();

    public abstract String readValue(String key);

    public abstract void write(String key, String value);

    public abstract void updateValue(String key, String value);

    public abstract void remove(String key);

    public abstract boolean exists(String key);

    public boolean canCreateIfNotExists(){
        return this.canCreateIfNotExists;
    }

}
