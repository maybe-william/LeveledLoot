package com.maybewilliam.leveledloot;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.LootPool;
import java.util.Random;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.Instance;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION, acceptedMinecraftVersions = References.ACCEPTED, modLanguage = "scala")
object LeveledLoot
{

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = {
    //this.logger = event.getModLog();
    //MinecraftForge.EVENT_BUS.register((LootEventHandler).getClass());
    MinecraftForge.EVENT_BUS.register(new LootEventHandler);
  }

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {
    // some example code
    //this.logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
  }

}

