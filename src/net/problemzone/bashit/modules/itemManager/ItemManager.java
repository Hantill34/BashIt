package net.problemzone.bashit.modules.itemManager;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import java.util.Random;

public class ItemManager {

    private static final int X_BREITE = 107;

    private static final int Z_BREITE = 45;

    private static final int X_START = -926;

    private static final int Z_START = -1039;

    private static final int MAX_Y = 47;

    public void generateChest(World world){
        Random random = new Random();
        Block block;
        do{
            int x = X_START - random.nextInt(X_BREITE);
            int z = Z_START - random.nextInt(Z_BREITE);

            block = world.getHighestBlockAt(x, z);

        }while(block.getY() > MAX_Y);

        block.getRelative(BlockFace.UP).setType(Material.CHEST);

    }

    public void generateEmeraldBlock(World world){
        Random random = new Random();
        Block block;

        do{
            int x = X_START - random.nextInt(X_BREITE);
            int z = Z_START - random.nextInt(Z_BREITE);

            block = world.getHighestBlockAt(x, z);

        }while(block.getY() > MAX_Y);

        block.getRelative(BlockFace.UP).setType(Material.EMERALD_BLOCK);
    }


}
