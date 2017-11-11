package com.cqrs.cqrs;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class LocalCommandBus implements CommandBus {

    private Map<CommandType, Consumer<Command>> listeners = new HashMap<>();

    public void register(CommandType type, Consumer<Command> listener) {
        listeners.put(type, listener);
    }
    public void dispatch(Command command) {
        listeners.getOrDefault(command.getType(), (c) -> {}).accept(command);
    }
}
