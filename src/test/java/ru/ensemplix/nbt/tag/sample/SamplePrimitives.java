package ru.ensemplix.nbt.tag.sample;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.ensemplix.nbt.annotation.NBT;

@ToString
@EqualsAndHashCode
public class SamplePrimitives {

    @NBT("byte")
    public byte sampleByte;

    @NBT("short")
    public short sampleShort;

    @NBT("integer")
    public int sampleInteger;

    @NBT("long")
    public long sampleLong;

    @NBT("float")
    public float sampleFloat;

    @NBT("double")
    public double sampleDouble;

    @NBT
    public String string;

    public String emptyString;

    public void fill() {
        sampleByte = 1;
        sampleShort = 23;
        sampleInteger = 69;
        sampleDouble = 3.4D;
        sampleFloat = 5.6F;
        sampleLong = 87900876675L;
        string = "I Love Koala <3";
    }

}
