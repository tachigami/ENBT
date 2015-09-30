package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;

import static ru.ensemplix.nbt.tag.TagType.*;

public class FloatTag extends AbstractTag<Float> {

    public FloatTag(String name, Float value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = in.readFloat();
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        out.writeFloat(value);
    }

    @Override
    public TagType getType() {
        return FLOAT;
    }

}
