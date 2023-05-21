package pl.shadxw.driver.configuration;

import lombok.Getter;

public enum CloudAppType {

    DRIVER("driver"),
    MASTER("pl/shadxw/master"),
    UNKNOWN("unknown");

    @Getter private final String value;

    CloudAppType(String value){
        this.value = value;
    }

    public static CloudAppType getTypeByName(String name){
        for(CloudAppType type : CloudAppType.values()){
            if(type.value.equalsIgnoreCase(name)) return type;
        }
        return UNKNOWN;
    }

}
