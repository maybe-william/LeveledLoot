/*
This file is for the changing of loot through event handlers
 */
package com.maybewilliam.leveledloot

import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.Logger
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraft.util.ResourceLocation
import net.minecraft.world.storage.loot.functions.LootFunction
import net.minecraft.world.storage.loot.conditions.LootCondition
import net.minecraft.world.storage.loot.RandomValueRange
import net.minecraft.world.storage.loot.LootEntryItem
import net.minecraft.world.storage.loot.LootTableList
import net.minecraft.world.storage.loot.functions.SetCount
import net.minecraft.world.storage.loot.LootPool
import java.util.Random
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod.Instance
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent

/** An event handler for block loot drops
 *
 *  @constructor create a new handler
 */
class LootEventHandler
{

  /** Changes harvest drops from broken blocks
   *
   *  @param event The harvest drop event
   */
  @SubscribeEvent
  def onHarvestDrops(event: HarvestDropsEvent): Unit = {
    val harvester = event.getHarvester
    if (harvester != null) {
      Debug.chat(harvester.experienceLevel.toString, harvester)
      val list = event.getDrops
      list.clear()
    }
  }
}
