package pl.shadxw.core.protocol.minecraft;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public enum MinecraftClientState {
    STATUS(1),
    LOGIN(2),
    PLAYING(3),
    LEFT(4),
    STATE_NOT_FOUND(0);

    @Getter private final int stateId;

    MinecraftClientState(int stateId){
        this.stateId = stateId;
    }

    public static Optional<MinecraftClientState> getStateByID(int id){
        return Arrays.stream(values()).filter(state -> state.getStateId() == id).findFirst();
    }

}
