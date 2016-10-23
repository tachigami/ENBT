package ru.ensemplix.nbt.converter;

import ru.ensemplix.nbt.annotation.NBT;
import ru.ensemplix.nbt.tag.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.ensemplix.nbt.tag.TagType.COMPOUND;
import static ru.ensemplix.nbt.tag.TagType.LIST;

public class ObjectConverter {

    public static Tag toTag(String compoundName, Object obj) throws IllegalAccessException {
        CompoundTag compoundTag = new CompoundTag(compoundName);
        boolean hasNBTFields = false;

        for(Field field : getFields(obj.getClass())) {
            NBT nbt = field.getAnnotation(NBT.class);

            if(nbt != null) {
                field.setAccessible(true);
                hasNBTFields = true;

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
                            if (fieldValue != null) {
                                TagType fieldType = TagType.getType(fieldValue.getClass());

                                if (fieldType != null) {
                                    listValue.add(fieldType.createTag(name, fieldValue));
                                } else {
                                    listValue.add(toTag(name, fieldValue));
                                }
                            }
                        }
                    }
                } else if(field.getType() == boolean.class) {
                    type = TagType.getType(byte.class);
                    tag = type.createTag(name, (byte) (((boolean) value) == true ? 1 : 0));
                } else {
                    tag = toTag(name, field.get(obj));
                }

                compoundTag.setTag(tag);
            }
        }

        if(!hasNBTFields && Enum.class.isAssignableFrom(obj.getClass())) {
            return new IntegerTag(compoundName, ((Enum) obj).ordinal());
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

        for(Field field : getFields(cls)) {
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
                        } else if(Enum.class.isAssignableFrom(field.getType())) {
                            field.set(obj, field.getType().getEnumConstants()[((int) value)]);
                        } else {
                            field.set(obj, value);
                        }
                    }
                }
            }
        }

        return obj;
    }

    private static List<Field> getFields(Class cls) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(cls.getDeclaredFields()));

        if(cls.getSuperclass() != null) {
            fields.addAll(Arrays.asList(cls.getSuperclass().getDeclaredFields()));
        }

        return fields;
    }


}
