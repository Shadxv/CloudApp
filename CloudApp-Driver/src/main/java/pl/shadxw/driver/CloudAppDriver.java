package pl.shadxw.driver;

import lombok.Getter;
import pl.shadxw.core.server.Server;
import pl.shadxw.driver.console.Console;
import pl.shadxw.driver.console.IConsole;
import pl.shadxw.master.server.MinecraftServer;

public class CloudAppDriver {


    @Getter private static CloudAppDriver driver;

    public static void main(String[] args){
        try{
            driver = new CloudAppDriver();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Getter private IConsole console;
    @Getter private Server minecraftServer;

    public CloudAppDriver() throws Exception {
        this.console = new Console();
        (this.minecraftServer = new MinecraftServer(25565, "localhost")).run();
    }

}
