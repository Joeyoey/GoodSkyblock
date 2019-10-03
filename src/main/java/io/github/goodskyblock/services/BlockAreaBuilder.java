package io.github.goodskyblock.services;

import io.github.goodskyblock.utilobjects.FakeBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockAreaBuilder {

    public static void writeToFile(File f, Map<String, Map<Vector, FakeBlock>> map) throws IOException {

        for (Map.Entry<String, Map<Vector, FakeBlock>> alpha : map.entrySet()) {
            File buildingFile = new File(f.getAbsolutePath(), alpha.getKey() + ".barea");
            buildingFile.createNewFile();

            // Create an output stream
            DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(buildingFile)));

            // Delegate writing to the stream to a separate method
            writeToStream(stream, alpha.getValue());

            // Always be sure to flush & close the stream.
            stream.flush();
            stream.close();

        }
    }

    /**
     * This method takes in a File and converts it to a live block area map that can be deployed.
     * @param files list of files to be read.
     * @return the Map of active block areas
     * @throws IOException For file reading problems
     * @throws NullPointerException For previous writing issues
     */
    public static Map<String, Map<Vector, FakeBlock>> readFromFile(File[] files) throws IOException, NullPointerException {

        Map<String, Map<Vector, FakeBlock>> map = new HashMap<>();
        if (files == null) {
            Bukkit.getLogger().severe("Files array is null");
            return null;
        }

        for (File file : files) {
            String fileName = file.getName().replaceAll(".barea", "");
            Bukkit.getLogger().severe(fileName + " has been loaded.");
            // Create an input stream
            DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

            // Delegate reading from the stream to a separate method
            map.put(fileName, readFromStream(stream));

            stream.close();
        }

        return map;
    }

    /**
     * Writes the map to a stream so that it can be saved in a file.
     * @param stream the stream to be written to.
     * @param map the map that contains the objects to be written.
     * @throws IOException This can throw an IO Exception if something goes wrong.
     */
    private static void writeToStream(DataOutputStream stream, Map<Vector, FakeBlock> map) throws IOException {

        // First, write the number of entries in the map.
        stream.writeInt(map.size());

        // Next, iterate through all the entries in the map
        for (Map.Entry<Vector, FakeBlock> entry : map.entrySet()) {

            // Write the x value of the vector
            stream.writeDouble(entry.getKey().getX());
            // Write the y value of the vector
            stream.writeDouble(entry.getKey().getY());
            // Write the z value of the vector
            stream.writeDouble(entry.getKey().getZ());
            // Write the length of the Material name
            stream.writeInt(entry.getValue().getMat().name().length());
            // Writes the Material name to the stream.
            stream.writeChars(entry.getValue().getMat().name());
            // Write the byte for the data of the FakeBlock
            stream.writeByte(entry.getValue().getData());
            // Write whether the block is a chest or not
            stream.writeBoolean(entry.getValue().isChest());

            if (entry.getValue().isChest()) {
                // Write the length of the array
                stream.writeInt(entry.getValue().getItemStacks().length);

                for (ItemStack is : entry.getValue().getItemStacks()) {
                    // Converts the ItemStack to a string.
                    String itemString = serializeItemStack(is);
                    // Writes the length of the string
                    stream.writeInt(itemString.length());
                    // Writes the characters to the stream
                    stream.writeChars(itemString);
                }
            }

        }

    }


    private static Map<Vector, FakeBlock> readFromStream(DataInputStream stream) throws IOException {

        // Create the data structure to contain the data from my custom file
        Map<Vector, FakeBlock> map = new HashMap<>();

        // Read the number of entries in this file
        int entryCount = stream.readInt();

        // Iterate through all the entries in the file, and add them to the map
        for (int i = 0; i < entryCount; i++) {

            // Reads the next double as value X
            double x = stream.readDouble();
            // Reads the next double as value Y
            double y = stream.readDouble();
            // Reads the next double as value Z
            double z = stream.readDouble();
            // Reads the next int as value mat
            int matLength = stream.readInt();
            String materialString = "";
            // Reads the material string from the stream.
            for (int a = 0; a < matLength; a++) materialString += stream.readChar();
            // Converts the material from string to Material.
            Material mat = Material.matchMaterial(materialString);
            // Reads the next byte as value data
            byte data = stream.readByte();
            // Reads whether the block is a chest or not
            boolean chest = stream.readBoolean();

            if (chest) {
                // Reads the length of the item array (contents in the chest)
                int itemArrayLength = stream.readInt();
                // Creates an array with the size of itemArrayLength to which the items will be added
                ItemStack[] contents = new ItemStack[itemArrayLength];
                for (int j = 0; j < itemArrayLength; j++) {
                    // Reads the length of the string that the serialized ItemStack was converted to.
                    int itemStringSize = stream.readInt();
                    StringBuilder itemString = new StringBuilder();
                    for (int k = 0; k < itemStringSize; k++) {
                        // Reads the chars for the length that was provided
                        itemString.append(stream.readChar());
                    }
                    // Adds the newly created ItemStack to the contents array.
                    contents[j] = deserializeItemStack(itemString.toString());
                }
                // Add the Objects to the map.
                map.put(new Vector(x, y, z), new FakeBlock(mat, data, contents));
            } else {
                // Add the Objects to the map.
                map.put(new Vector(x, y, z), new FakeBlock(mat, data));
            }
        }

        return map;

    }



    private static ItemStack deserializeItemStack(String data){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        ItemStack itemStack = null;
        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            Class<?> nmsItemStackClass = getNMSClass("ItemStack");
            Object nbtTagCompound = Objects.requireNonNull(getNMSClass("NBTCompressedStreamTools")).getMethod("a", DataInputStream.class).invoke(null, dataInputStream);
            //Object nbtTagCompound = getNMSClass("NBTCompressedStreamTools").getMethod("a", DataInputStream.class).invoke(null, inputStream);
            assert nmsItemStackClass != null;
            Object craftItemStack = nmsItemStackClass.getMethod("createStack", nbtTagCompoundClass).invoke(null, nbtTagCompound);
            itemStack = (ItemStack) Objects.requireNonNull(getOBClass("inventory.CraftItemStack")).getMethod("asBukkitCopy", nmsItemStackClass).invoke(null, craftItemStack);
        } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return itemStack;
    }

    private static String serializeItemStack(ItemStack item) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            assert nbtTagCompoundClass != null;
            Constructor<?> nbtTagCompoundConstructor = nbtTagCompoundClass.getConstructor();
            Object nbtTagCompound = nbtTagCompoundConstructor.newInstance();
            Object nmsItemStack = Objects.requireNonNull(getOBClass("inventory.CraftItemStack")).getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            Objects.requireNonNull(getNMSClass("ItemStack")).getMethod("save", nbtTagCompoundClass).invoke(nmsItemStack, nbtTagCompound);
            Objects.requireNonNull(getNMSClass("NBTCompressedStreamTools")).getMethod("a", nbtTagCompoundClass, DataOutput.class).invoke(null, nbtTagCompound, (DataOutput)dataOutput);

        } catch (SecurityException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //return BaseEncoding.base64().encode(outputStream.toByteArray());
        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private static Class<?> getOBClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
            return null;
        }
    }

}
