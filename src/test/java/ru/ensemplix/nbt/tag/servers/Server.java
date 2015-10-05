package ru.ensemplix.nbt.tag.servers;

import ru.ensemplix.nbt.annotation.NBT;


public class Server {

    @NBT
    public String name;

    @NBT
    public String ip;

    @NBT
    public boolean hideAddress;

}
