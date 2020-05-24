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
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.LootPool;
import java.util.Random;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

class LootEventHandler
{

  //@EventHandler
  //def preInit(event: FMLPreInitializationEvent): Unit = {
    //this.logger = event.getModLog();
    //MinecraftForge.EVENT_BUS.register(new LootEventHandler());
  //}
  //
  @SubscribeEvent
  def onHarvestDrops(event: HarvestDropsEvent): Unit = {
    val harvester = event.getHarvester
    if (harvester != null) {
      Debug.chat(harvester.experienceLevel.toString, harvester)
      val list = event.getDrops
      list.clear()
    }
  }

  @SubscribeEvent
  def onLootTableLoad(event: LootTableLoadEvent): Unit = {
    if (true) {//event.getName().equals(LootTableList.ENTITIES_PIG)) {//new ResourceLocation("minecraft","blocks/stone"))) {
      var cond = new LootCondition() {
        def testCondition(x: Random, y: LootContext): Boolean = {
          return true;
        }
      }
      var func = new LootFunction(Array(cond)) {
        def apply(i: ItemStack, r: Random, context: LootContext): ItemStack = {
          return new ItemStack(Items.FEATHER, 3);
        }
      }//, new RandomValueRange(1, 5))) {}
      var x = event.getTable();
      var y = x.getPool("main")
      //both loot function and loot condition are abstract. using syntax [] and [0]
      if (y != null){
        var z = y.addEntry(new LootEntryItem(Items.FEATHER, 10, 0, Array(func), Array(cond), "leveledloot:feather"));
      }
    }
  }
}
