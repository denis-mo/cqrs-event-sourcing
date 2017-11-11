package com.cqrs.command;

import com.cqrs.cqrs.Command;
import com.cqrs.cqrs.CommandType;

public class CreateCustomerCommand extends Command {

    public final String name;
    public final long balance;

    public CreateCustomerCommand(String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

    public String createCode(){
        return name.toLowerCase();
    }

    @Override
    public CommandType getType() {
        return CommandType.CREATE_CUSTOMER;
    }
}
