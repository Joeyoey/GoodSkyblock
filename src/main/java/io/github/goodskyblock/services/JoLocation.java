package io.github.goodskyblock.services;

import java.io.Serializable;

import org.bukkit.Location;


public final class JoLocation implements Serializable {

    /**
     * serialVersionUID by Eclipse
     */
    private static final long serialVersionUID = 7135831682192122814L;

    final String world;
    final double x;
    final double y;
    final double z;

    /**
     * Constructs a new location with the given world and x, y and z coordinate.
     * @param world the world
     * @param d the x coordinate
     * @param e the y coordinate
     * @param f the z coordinate
     */
    public JoLocation(String world, double d, double e, double f) {
        this.world = world;
        this.x = d;
        this.y = e;
        this.z = f;
    }

    public JoLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
    }


    /**
     * Returns the x coordinate of the location.
     * @return the x coordinate of the location
     */
    public double getBlockX() {
        return x;
    }

    /**
     * Returns the y coordinate of the location.
     * @return the y coordinate of the location
     */
    public double getBlockY() {
        return y;
    }

    public String getWorld() {
        return world;
    }


    /**
     * Returns the z coordinate of the location.
     * @return the z coordinate of the location
     */
    public double getBlockZ() {
        return z;
    }

    /**
     * Returns the block at this location.
     * @return the block at this location
     */
    public Location getBlock() {
        return new Location(org.bukkit.Bukkit.getServer().getWorld(world), x, y, z);
    }

    /**
     * Returns the custom version of Bukkit's Location.
     * @param loc the Bukkit Location
     * @return the custom version of Bukkit's Location
     */
    public static JoLocation getLocationFromLocation(org.bukkit.Location loc) {
        JoLocation result = null;

        if (loc != null) {
            result = new JoLocation(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        }

        return result;
    }


    /**
     * This gets a JoLocation from a provided String
     * @param begin start of the string.
     * @return a new JoLocation
     */
    public static JoLocation jLocFromString(String begin) {
        String[] parts = begin.split(",");
        String world = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        return new JoLocation(world, x, y, z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return world + "," + x + "," + y + "," + z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JoLocation)) {
            return false;
        }

        JoLocation loc = (JoLocation) o;

        return this.world.equals(loc.world) && this.x == loc.x && this.y == loc.y && this.z == loc.z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;

        hash = 19 * hash + (this.world != null ? this.world.hashCode() : 0);
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }



}