package ru.ensemplix.nbt.tag;

import ru.ensemplix.nbt.stream.NBTInputStream;
import ru.ensemplix.nbt.stream.NBTOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.ensemplix.nbt.tag.TagType.*;

public class ListTag extends AbstractTag<List<Tag>> {

    public ListTag() {
        super("", new ArrayList<>());
    }

    public ListTag(String name) {
        super(name, new ArrayList<>());
    }

    public ListTag(String name, List<Tag> value) {
        super(name, value);
    }

    @Override
    public void readTag(NBTInputStream in) throws IOException {
        value = new ArrayList<>();

        TagType type = in.readTagType();

        if(type == END) {
            return;
        }

        int length = in.readInt();

        for(int i = 0; i < length; i++) {
            value.add(in.readTag());
        }
    }

    @Override
    public void writeTag(NBTOutputStream out) throws IOException {
        if(!value.isEmpty()) {
            out.writeByte(value.get(0).getType().ordinal());
        } else {
            out.writeByte(0);
        }

        out.writeInt(value.size());

        for(Tag tag : value) {
            out.writeTag(tag);
        }
    }

    @Override
    public TagType getType() {
        return LIST;
    }

}
