package ru.ensemplix.nbt.tag.servers;

import ru.ensemplix.nbt.annotation.NBT;

import java.util.ArrayList;
import java.util.List;

public class ServerList {

    @NBT
    public List<Server> servers = new ArrayList<>();

}
