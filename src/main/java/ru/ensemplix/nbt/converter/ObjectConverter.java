package ru.ensemplix.nbt.converter;

import ru.ensemplix.nbt.annotation.NBT;
import ru.ensemplix.nbt.tag.CompoundTag;
import ru.ensemplix.nbt.tag.ListTag;
import ru.ensemplix.nbt.tag.Tag;
import ru.ensemplix.nbt.tag.TagType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static ru.ensemplix.nbt.tag.TagType.COMPOUND;
import static ru.ensemplix.nbt.tag.TagType.LIST;

public class ObjectConverter {

    public static CompoundTag toTag(String compoundName, Object obj) throws IllegalAccessException {
        CompoundTag compoundTag = new CompoundTag(compoundName);

        for (Field field : obj.getClass().getDeclaredFields()) {
            NBT nbt = field.getAnnotation(NBT.class);

            if (nbt != null) {
                field.setAccessible(true);

                String name = nbt.value().isEmpty() ? field.getName() : nbt.value();
                TagType type = TagType.getType(field.getType());
                Object value = field.get(obj);
                Tag tag;

                if(value == null) {
                    continue;
                }

                if(type != null) {
                    if(type != LIST) {
                        tag = type.createTag(name, value);
                    } else {
                        tag = type.createTag(name, new ArrayList<>());
                        ListTag listTag = (ListTag) tag;
                        List<Tag> listValue = listTag.getValue();

                        for(Object fieldValue : (List) value) {
                            if(fieldValue != null) {
                                TagType fieldType = TagType.getType(fieldValue.getClass());

                                if (fieldType != null) {
                                    listValue.add(fieldType.createTag(name, fieldValue));
                                } else {
                                    listValue.add(toTag(name, fieldValue));
                                }
                            }
                        }
                    }
                } else {
                    tag = toTag(name, field.get(obj));
                }

                compoundTag.setTag(tag);
            }
        }

        return compoundTag;
    }

    public static <T> T toObject(Class<T> cls, CompoundTag compoundTag) throws IllegalAccessException {
        T obj;

        try {
            obj = cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Failed create NBT object from " + cls.getName(), e);
        }

        for (Field field : cls.getDeclaredFields()) {
            NBT nbt = field.getAnnotation(NBT.class);

            if(nbt != null) {
                field.setAccessible(true);

                String name = nbt.value().isEmpty() ? field.getName() : nbt.value();
                Tag tag = compoundTag.getValue(name);

                if(tag != null) {
                    if(tag.getType() == LIST) {
                        List<Object> list = new ArrayList<>();

                        for(Object listValue : (List) tag.getValue()) {
                            Tag listTag = (Tag) listValue;

                            if(listTag.getType() != COMPOUND) {
                                list.add(listTag.getValue());
                            } else {
                                ParameterizedType type = (ParameterizedType) field.getGenericType();
                                Class<?> listClass = (Class<?>) type.getActualTypeArguments()[0];
                                list.add(toObject(listClass, (CompoundTag) listTag));
                            }
                        }

                        field.set(obj, list);
                    } else if(tag.getType() == COMPOUND) {
                        field.set(obj, toObject(field.getType(), (CompoundTag) tag));
                    } else {
                        Object value = tag.getValue();

                        if(value.getClass() == Byte.class && field.getType() == boolean.class) {
                            field.set(obj, (byte) value == 1);
                        } else {
                            field.set(obj, value);
                        }
                    }
                }
            }
        }

        return obj;
    }


}
