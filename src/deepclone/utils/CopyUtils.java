package deepclone.utils;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CopyUtils {

    private static final List<Class<?>> DEEP_COPY_EXCLUDED_CLASSES =
            Arrays.asList(String.class, Character.class, Boolean.class);

    @SuppressWarnings("unchecked")
    public static Object deepCopy(Object src) {
        if (src == null)
            return null;

        Class<?> objClass = src.getClass();
        if (isNoNeedDeepCopy(objClass)) {
            return src;
        }
        if (objClass.isArray()) {
            return copyArray(src);
        }
        if (src instanceof Collection) {
            return copyCollection((Collection<Object>) src);
        }

        Object copy = createObject(objClass);
        copyFields(src, copy, objClass);
        return copy;
    }

    private static Object createObject(Class<?> objClass) {
        Object result = null;
        try {
            Constructor<?> constructor = objClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            result = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // ignored
        }

        if (result == null) {
            Constructor<?>[] constructorList = objClass.getDeclaredConstructors();
            for (Constructor<?> c : constructorList) {
                if (result != null)
                    return result;

                Parameter[] parameters = c.getParameters();
                Object[] params = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Parameter p = parameters[i];
                    if (p.getType() == boolean.class) {
                        params[i] = false;
                    } else if (p.getType() == char.class) {
                        params[i] = ' ';
                    } else if (p.getType().isPrimitive()) {
                        params[i] = 0;
                    }
                }

                try {
                    c.setAccessible(true);
                    result = c.newInstance(params);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    // ignored
                }
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static void copyFields(Object src, Object copy, Class<?> objClass) {
        try {
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isFinal(field.getModifiers())
                        || Modifier.isStatic(field.getModifiers())
                        || Modifier.isTransient(field.getModifiers())
                        || !field.trySetAccessible()) {
                    continue;
                }
                field.setAccessible(true);

                Object fieldValue = field.get(src);
                Class<?> fieldClass = field.getType();
                if (fieldValue == null) {
                    continue;
                }

                if (isNoNeedDeepCopy(fieldClass)) {
                    field.set(copy, fieldValue);
                } else if (src.equals(fieldValue)) {    // if self-reference
                    field.set(copy, copy);
                } else if (fieldValue instanceof Collection) {
                    field.set(copy, copyCollection((Collection<Object>) fieldValue));
                } else if (fieldClass.isArray()) {
                    field.set(copy, copyArray(fieldValue));
                } else {
                    Object objCopy = deepCopy(fieldValue);
                    field.set(copy, objCopy);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static boolean isNoNeedDeepCopy(Class<?> objClass) {
        return objClass.isPrimitive()
                || Number.class.isAssignableFrom(objClass)
                || DEEP_COPY_EXCLUDED_CLASSES.contains(objClass);
    }

    @SuppressWarnings("unchecked")
    private static Collection<Object> copyCollection(Collection<Object> srcCollection) {
        Collection<Object> copyCollection = (Collection<Object>) createObject(srcCollection.getClass());
        if (copyCollection != null) {
            for (Object obj : srcCollection) {
                Object objCopy = deepCopy(obj);
                copyCollection.add(objCopy);
            }
        }
        return copyCollection;
    }

    private static Object copyArray(Object srcArray) {
        Object arrayCopy = Array.newInstance(srcArray.getClass().getComponentType(), Array.getLength(srcArray));
        int length = Array.getLength(arrayCopy);
        for (int i = 0; i < length; i++) {
            Object objCopy = deepCopy(Array.get(srcArray, i));
            Array.set(arrayCopy, i, objCopy);
        }
        return arrayCopy;
    }
}
