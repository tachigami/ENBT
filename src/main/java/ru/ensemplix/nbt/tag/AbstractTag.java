package ru.ensemplix.nbt.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class AbstractTag<T> implements Tag<T> {

    @Getter
    private String name;

    @Getter
    protected T value;

    public AbstractTag(String name) {
        this.name = name;
    }

}
