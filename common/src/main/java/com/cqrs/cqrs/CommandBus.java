package com.cqrs.cqrs;

import java.util.function.Consumer;

public interface CommandBus {

    void register(CommandType type, Consumer<Command> listener);

    void dispatch(Command event);
}
