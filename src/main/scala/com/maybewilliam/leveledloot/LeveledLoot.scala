package com.maybewilliam.leveledloot;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = LeveledLoot.MODID, name = LeveledLoot.NAME, version = LeveledLoot.VERSION)
class LeveledLoot
{

    var logger: Logger = null;

    @EventHandler
    def preInit(event: FMLPreInitializationEvent): Unit = {
        //this.logger = event.getModLog();
    }

    @EventHandler
    def init(event: FMLInitializationEvent): Unit = {
        // some example code
        //this.logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}

object LeveledLoot
{
  final val MODID = "leveledloot";
  final val NAME = "Leveled Loot";
  final val VERSION = "0.1";
}
