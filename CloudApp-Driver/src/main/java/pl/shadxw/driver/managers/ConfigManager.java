package pl.shadxw.driver.managers;

import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;
import pl.shadxw.core.console.MessageType;
import pl.shadxw.core.models.ConfigFile;
import pl.shadxw.driver.CloudAppDriver;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager extends ConfigFile {

    private static final String CONFIG_FILE_NAME = "config.yaml";

    private final String finalPath;
    private final String templateName;
    private final File configFile;
    private final Yaml configYaml;
    private Map<String, Object> config;

    public ConfigManager(String path, String templateName ,boolean canCreateIfNotExists) {
        super(path, canCreateIfNotExists);
        this.templateName = templateName;
        this.finalPath = path + "/" + CONFIG_FILE_NAME;
        CloudAppDriver.getApp().getConsole().writeLine("Checking configuration files...", MessageType.NORMAL);
        this.configFile = new File(this.finalPath);
        this.configYaml = new Yaml();
        try {
            loadConfigFile();
        } catch (IOException e) {
            CloudAppDriver.getApp().getConsole().writeLine("Could not find configuration files!", MessageType.WARNING);
            if (super.canCreateIfNotExists()) create();
            else {
                CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not create files!", MessageType.ERROR);
                CloudAppDriver.getApp().shutdown(true, true);
            }
        }
    }

    @SneakyThrows
    @Override
    public void forceCreate(String templateFileName, boolean load) {
        File configFolder = new File(super.getPath());
        if(!configFolder.isDirectory()) configFolder.mkdir();
        try {
            InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream(templateFileName);
            File config = new File(this.finalPath);
            config.createNewFile();
            FileOutputStream configStream = new FileOutputStream(config);
            configStream.write(templateStream.readAllBytes());

            templateStream.close();
            configStream.close();

            if(load) this.loadConfigFile();
        } catch (FileNotFoundException e) {
            CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not find '" + CONFIG_FILE_NAME + "' template in resources!", MessageType.ERROR);
            CloudAppDriver.getApp().shutdown(true, true);
        } catch (IOException e) {
            CloudAppDriver.getApp().getConsole().writeLine("CloudApp could not create or read '" + CONFIG_FILE_NAME + "' !", MessageType.ERROR);
            CloudAppDriver.getApp().shutdown(true, true);
        }

    }

    @Override
    public void loadConfigFile() throws IOException {
        CloudAppDriver.getApp().getConsole().writeLine("Loading configuration files...", MessageType.NORMAL);
        InputStream inputStream = new FileInputStream(configFile);
        this.config = this.configYaml.load(inputStream);
        inputStream.close();
    }

    @SneakyThrows
    @Override
    protected void create() {
        CloudAppDriver.getApp().getConsole().write("CloudApp needs to create configuration files. Do you agree to create them there? (Y/n): ", MessageType.NORMAL);
        String input = null;
        do {
            if(input != null) CloudAppDriver.getApp().getConsole().write("You entered wrong value. Do you agree to create configuration files there? (Y/n): ", MessageType.NORMAL);
            input = CloudAppDriver.getApp().getConsole().getScanner().nextLine();
        } while (!input.isEmpty()
                && !input.equalsIgnoreCase(" ")
                && !input.equalsIgnoreCase("y")
                && !input.equalsIgnoreCase("n"));
        if(input.equalsIgnoreCase("n")){
            CloudAppDriver.getApp().getConsole().writeLine("Could not create configuration files...", MessageType.WARNING);
            CloudAppDriver.getApp().shutdown(true, true);
        } else {
            this.forceCreate(this.templateName, true);
        }
    }

    @Override
    public Object readValue(String key) {
        if(this.exists(key)) return this.config.get(key);
        return null;
    }

    @Override
    public void updateValue(String key, Object value) throws IOException {
        if(this.exists(key)){
            this.config.put(key, value);
            List<String> lines = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(configFile));

            String line;
            while ((line = reader.readLine()) != null){
                String[] components = line.split(": ");
                if(components.length != 0) {
                    if (this.config.containsKey(components[0]) && components[0].equalsIgnoreCase(key)) {
                        try {
                            lines.add(components[0] + ": " + Integer.parseInt(value.toString()));
                        } catch (NumberFormatException e){
                            lines.add(components[0] + ": \"" + value + "\"");
                        }
                    } else {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);
                }

            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
            for(String s : lines) {
                s = s.replace("\n", "\\n");
                writer.write(s + "\n");
            }
            writer.close();
        }
    }

    @Override
    public boolean exists(String key) {
        return this.config != null && this.config.containsKey(key);
    }
}
