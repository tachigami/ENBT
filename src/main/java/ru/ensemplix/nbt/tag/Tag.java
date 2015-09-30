package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

public interface Tag<T> {

    String getName();

    T getValue();

    void readTag(NBTInputStream in) throws IOException;

    void writeTag(NBTOutputStream out) throws IOException;

    TagType getType();

}
