package ru.ensemplix.nbt.tag.sample;

import ru.ensemplix.nbt.annotation.NBT;

import java.util.ArrayList;
import java.util.List;

public class SampleCompound {

    @NBT
    public SamplePrimitives primitives = new SamplePrimitives();

    @NBT
    public SampleArraysAndList arraysAndList = new SampleArraysAndList();

    @NBT
    public List<SamplePrimitives> listPrimitives = new ArrayList<>();

    public void fill() {
        primitives.fill();
        arraysAndList.fill();

        for(int i = 0; i < 5; i++) {
            SamplePrimitives listPrimitive = new SamplePrimitives();
            listPrimitive.fill();

            listPrimitives.add(listPrimitive);
        }
    }

}
