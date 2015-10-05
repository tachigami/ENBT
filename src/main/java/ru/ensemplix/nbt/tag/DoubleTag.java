package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.DOUBLE;

public class DoubleTag extends AbstractTag<Double> {

    public DoubleTag(String name, Double value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = in.readDouble();
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeDouble(value);
    }

    @Override
    public TagType getType() {
        return DOUBLE;
    }

}
