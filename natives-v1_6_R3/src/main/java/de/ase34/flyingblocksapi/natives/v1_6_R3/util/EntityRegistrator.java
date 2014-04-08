/**
 * flyingblocksapi Copyright (C) 2014 ase34 and contributors
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
/*
 * This code is pulled from Jacek (http://forums.bukkit.org/threads/tutorial-how-to-customize-the-behaviour-of-a-mob-or-entity.54547/)
 */
package de.ase34.flyingblocksapi.natives.v1_6_R3.util;

import java.lang.reflect.Method;

public class EntityRegistrator {
    
    private static Method registerMethod;
    
    static {
        try {
            registerMethod = net.minecraft.server.v1_6_R3.EntityTypes.class.getDeclaredMethod("a", new Class<?>[]{Class.class, String.class, int.class});
            registerMethod.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static void registerCustomEntity(Class entityClass, String name, int id) {
        try {
            registerMethod.invoke(null, entityClass, name, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
