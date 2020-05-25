/*
This is the file containing the object for the main mod.
It's essentially the main method of the mod.
*/
package com.maybewilliam.leveledloot

import java.util.Random
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger

/** Mod object */
@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION, acceptedMinecraftVersions = References.ACCEPTED, modLanguage = "scala")
object LeveledLoot
{

  /** Does pre init setup
   *
   *  @param event the pre init object
   */
  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = {
    //this.logger = event.getModLog()
    //MinecraftForge.EVENT_BUS.register((LootEventHandler).getClass())
    MinecraftForge.EVENT_BUS.register(new LootEventHandler)
  }

  /** Does init setup
   *
   *  @param event the init object
   */
  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {
    // some example code
    //this.logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName())
  }
}

