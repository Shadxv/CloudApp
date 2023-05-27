package pl.shadxw.core.models;

import lombok.Getter;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class ConfigFile {

    @Getter private final String path;
    private final boolean canCreateIfNotExists;

    protected ConfigFile(String path, boolean canCreateIfNotExists){
        this.path = path;
        this.canCreateIfNotExists = canCreateIfNotExists;
    }

    public abstract void forceCreate(String templateFileName, boolean load);

    public abstract void loadConfigFile() throws IOException;

    protected abstract void create();

    public abstract Object readValue(String key);

    public abstract void updateValue(String key, Object value) throws IOException, URISyntaxException;

    public abstract boolean exists(String key);

    public boolean canCreateIfNotExists(){
        return this.canCreateIfNotExists;
    }

}
