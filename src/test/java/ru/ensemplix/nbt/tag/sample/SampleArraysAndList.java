package ru.ensemplix.nbt.tag.sample;

import lombok.ToString;
import ru.ensemplix.nbt.annotation.NBT;

import java.util.Arrays;
import java.util.List;

@ToString
public class SampleArraysAndList {

    public static final byte[] BYTE_ARRAY = new byte[] {1, 3, 5, 2, 3};
    public static final int[] INTEGER_ARRAY = new int[] {1, 3, 5, 2, 3};
    public static final String[] STRING_ARRAY = new String[] {"I", "Love", "Koala"};

    @NBT("list")
    public List<?> sampleList;

    @NBT("integerArray")
    public int[] sampleIntegerArray;

    @NBT("byteArray")
    public byte[] sampleByteArray;

    public void fill() {
        sampleByteArray = BYTE_ARRAY;
        sampleIntegerArray = INTEGER_ARRAY;
        sampleList = Arrays.asList(STRING_ARRAY);
    }

}
