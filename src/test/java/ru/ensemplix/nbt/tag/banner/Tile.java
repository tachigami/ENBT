package ru.ensemplix.nbt.tag.banner;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.ensemplix.nbt.annotation.NBT;

@NoArgsConstructor
@AllArgsConstructor
public class Tile {

    @NBT
    public int x;

    @NBT
    public int y;

    @NBT
    public int z;

}
