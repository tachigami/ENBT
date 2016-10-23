package ru.ensemplix.nbt.tag.servers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.ensemplix.nbt.annotation.NBT;

@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @NBT
    public String name;

    @NBT
    public String ip;

    @NBT
    public boolean hideAddress;

}
