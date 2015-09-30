package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.ensemplix.nbt.tag.TagType.COMPOUND;

public class CompoundTag extends AbstractTag<Map<String, Tag>> {

    public CompoundTag() {
        super("", new HashMap<>());
    }

    public CompoundTag(String name) {
        super(name, new HashMap<>());
    }

    public CompoundTag(String name, Map<String, Tag> value) {
        super(name, value);
    }

    public void setTag(Tag tag) {
        value.put(tag.getName(), tag);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = new HashMap<>();

        while(true) {
            Tag tag = in.readTag();

            if(tag == null) {
                break;
            }

            value.put(tag.getName(), tag);
        }
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        for(Tag tag : value.values()) {
            out.writeTag(tag);
        }

        out.writeByte(0);
    }

    @Override
    public TagType getType() {
        return COMPOUND;
    }

}
