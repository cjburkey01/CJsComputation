package com.cjburkey.cjscomputation.computer;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.ModInfo;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

public class ComputerHandler extends WorldSavedData {
    
    public static final String name = ModInfo.modid + "_computer_data";
    
    private static ComputerHandler instance;

    private short lastId = -1;
    private final Map<Short, ComputerWrapper> computers = new HashMap<>();
    
    public ComputerHandler() {
        this(name);
    }
    
    // Forge wants this constructor, though it should not be used
    public ComputerHandler(String name) {
        super(name);
        instance = this;
    }
    
    // Uses a good amount of reflection to load computer types dynamically
    // Probably (definitely) could be sped up and made more efficient
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt == null) {
            return;
        }
        
        if (nbt.hasKey("last_id")) {
            lastId = nbt.getShort("last_id");
        }
        if (nbt.hasKey("computers")) {
            computers.clear();
            NBTTagList list = nbt.getTagList("computers", new NBTTagCompound().getId());
            for (int i = 0; i < list.tagCount(); i ++) {
                NBTTagCompound computerTag = list.getCompoundTagAt(i);
                if (computerTag == null || !computerTag.hasKey("id") || !computerTag.hasKey("type")) {
                    continue;
                }
                short id = computerTag.getShort("id");
                String type = computerTag.getString("type");
                try {
                    Class<?> typeClass = Class.forName(type);
                    if (typeClass == null) {
                        throw new ClassNotFoundException("Class not found: " + type);
                    }
                    Class<? extends ComputerCore> instantiatedClass = (Class<? extends ComputerCore>) typeClass;
                    Constructor<? extends ComputerCore> constructor = instantiatedClass.getConstructor(short.class);
                    ComputerCore computer = constructor.newInstance(id);
                    if (computer == null) {
                        Debug.err("Failed to create computer {} of type {}", id, type);
                        continue;
                    }
                    if (!computer.loadData(computerTag)) {
                        Debug.err("Computer failed to load data {} of type {}", type, computer.id);
                        continue;
                    }
                    computers.put(computer.id, new ComputerWrapper(computer));
                } catch (Exception e) {
                    Debug.warn("Failed to load computer {}, the type {} could not be found or an error occurred creating an instance: {}", id, type, e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }
            Debug.log("Loaded {} computers from world", computers.size());
            return;
        }
        Debug.log("Failed to load computers from world: handler had no computers");
    }
    
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }
        
        nbt.setShort("last_id", lastId);
        NBTTagList list = new NBTTagList();
        for (ComputerWrapper computer : computers.values()) {
            NBTTagCompound computerTag = new NBTTagCompound();
            computerTag.setShort("id", computer.computer.id);
            computerTag.setString("type", computer.type.getName());
            computer.computer.saveData(computerTag);
            list.appendTag(computerTag);
        }
        nbt.setTag("computers", list);
        Debug.log("Saved {} computers to world", computers.size());
        
        return nbt;
    }
    
    public <T extends ComputerCore> void addComputer(T computer) {
        if (!computers.containsKey(computer.id)) {
            computers.put(computer.id, new ComputerWrapper(computer));
            lastId = computer.id;
            markDirty();
            Debug.log("Created computer {}", computer.id);
        }
    }
    
    public <T extends ComputerCore> T getComputer(short id, String type) {
        try {
            return getComputer(id, (Class<T>) Class.forName(type));
        } catch (Exception e) {
        }
        Debug.err("Invalid class: {}", type);
        return null;
    }
    
    public <T extends ComputerCore> T getComputer(short id, Class<T> type) {
        ComputerCore computer = computers.get(id).computer;
        if (computer.getClass().equals(type)) {
            return type.cast(computer);
        }
        Debug.err("Computer of type {} not assignable by type {}", computer.getClass().getSimpleName(), type.getSimpleName());
        return null;
    }
    
    public short getLastId() {
        return lastId;
    }
    
    public short getNextId() {
        return (short) (lastId + 1);
    }
    
    public static ComputerHandler get() {
        MapStorage storage = DimensionManager.getWorld(0).getMapStorage();  // Global storage, computers are stored per-world, not per-dimension
        ComputerHandler instance = (ComputerHandler) storage.getOrLoadData(ComputerHandler.class, name);
        if (instance == null) {
            instance = new ComputerHandler();
            storage.setData(name, instance);
        }
        return instance;
    }
    
}