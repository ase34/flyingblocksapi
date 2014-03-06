/*
 * This code is pulled from Jogy34 
 * (https://forums.bukkit.org/threads/tutorial-1-7-creating-a-custom-entity.212849/)
 */
package de.ase34.flyingblocksapi.util;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityRegistrator {

    protected static Field mapStringToClassField, mapClassToStringField, mapClassToIdField, mapStringToIdField;

    static
    {
        try
        {
            mapStringToClassField = net.minecraft.server.v1_7_R1.EntityTypes.class.getDeclaredField("c");
            mapClassToStringField = net.minecraft.server.v1_7_R1.EntityTypes.class.getDeclaredField("d");
            // mapIdtoClassField = net.minecraft.server.v1_7_R1.EntityTypes.class.getDeclaredField("e");
            mapClassToIdField = net.minecraft.server.v1_7_R1.EntityTypes.class.getDeclaredField("f");
            mapStringToIdField = net.minecraft.server.v1_7_R1.EntityTypes.class.getDeclaredField("g");

            mapStringToClassField.setAccessible(true);
            mapClassToStringField.setAccessible(true);
            // mapIdToClassField.setAccessible(true);
            mapClassToIdField.setAccessible(true);
            mapStringToIdField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void registerCustomEntity(Class entityClass, String name, int id)
    {
        if (mapStringToClassField == null || mapStringToIdField == null || mapClassToStringField == null
                || mapClassToIdField == null)
        {
            return;
        }
        else
        {
            try
            {
                Map mapStringToClass = (Map) mapStringToClassField.get(null);
                Map mapStringToId = (Map) mapStringToIdField.get(null);
                Map mapClasstoString = (Map) mapClassToStringField.get(null);
                Map mapClassToId = (Map) mapClassToIdField.get(null);

                mapStringToClass.put(name, entityClass);
                mapStringToId.put(name, Integer.valueOf(id));
                mapClasstoString.put(entityClass, name);
                mapClassToId.put(entityClass, Integer.valueOf(id));

                mapStringToClassField.set(null, mapStringToClass);
                mapStringToIdField.set(null, mapStringToId);
                mapClassToStringField.set(null, mapClasstoString);
                mapClassToIdField.set(null, mapClassToId);
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
