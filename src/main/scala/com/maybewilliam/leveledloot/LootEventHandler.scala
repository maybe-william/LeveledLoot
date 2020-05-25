/*
This file is for the changing of loot through event handlers
 */
package com.maybewilliam.leveledloot

import java.util.List
import java.lang.Math
import java.util.Random
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.apache.logging.log4j.Logger

/** An event handler for block loot drops
 *
 *  @constructor create a new handler
 */
class LootEventHandler
{
  val rand = new Random

  /** Returns a drop stack if drop is an ore, otherwise null
   *
   *  @param event The HarvestDropsEvent
   *
   *  @returns The stack to return or null
   */
  def getDropStack(event: HarvestDropsEvent): ItemStack = {

    val num = event.getDrops.size
    if(num == 0) {
      return null
    }

    val stack = event.getDrops.get(0)
    var item = stack.getItem
    var mdata = stack.getMetadata

    val coal = item == Items.COAL
    val iron = item == Item.getItemFromBlock(Blocks.IRON_ORE)
    val gold = item == Item.getItemFromBlock(Blocks.GOLD_ORE)
    val diamond = item == Items.DIAMOND
    val emerald = item == Items.EMERALD
    val lapis = item == Items.DYE && mdata == 4
    val redstone = item == Items.REDSTONE
    val quartz = item == Items.QUARTZ

    var ore = coal || iron || gold || diamond
    ore = ore || emerald || lapis || redstone || quartz

    if (ore) {
      if (iron) {
        item = Items.IRON_INGOT
      }
      if (gold) {
        item = Items.GOLD_INGOT
      }
      if (lapis) {
        return new ItemStack(item, num, 4)
      }
      return new ItemStack(item, num)
    }
    return null
  }

  /** Changes harvest drops if block is an ore
   *
   *  @param level The block-breaker's level
   *  @param list The list of drops
   */
  def modifyHarvestDrops(level: Int, fortuneLevel: Int, dropStack: ItemStack, list: List[ItemStack]): Unit = {

    //Tier, Prob, and Boost referenced against each other should give roughly 2x the items every 30 levels
    val tier = Math.floor(level/30) + 2
    val prob = level % 30
    val boost = Math.round(Math.pow(2, (tier-1))).toInt

    //Get base amount of itemStacks to drop
    var count = (boost/2).toInt //give a guaranteed base amount of drops for each 30 levels
    var i = count
    for (i <- 0 until boost) {
      if (rand.nextInt(31) <= prob) {
        count = count + 1
      }
    }

    //Fortune enchantment calculation applied to amount
    if (fortuneLevel == 1) {
      if (rand.nextInt(101) <= 33) {
        count = count * 2
      }
    }
    if (fortuneLevel == 2) {
      if (rand.nextInt(101) <= 25) {
        count = count * 2
      }
      if (rand.nextInt(101) <= 25) {
        count = count * 3
      }
    }
    if (fortuneLevel == 3) {
      if (rand.nextInt(101) <= 20) {
        count = count * 2
      }
      if (rand.nextInt(101) <= 20) {
        count = count * 3
      }
      if (rand.nextInt(101) <= 20) {
        count = count * 4
      }
    }

    //If only 1 or 0 drops, don't modify original drop list at all
    if (count > 1) {
      list.clear()
      var j = 0
      for (j <- 0 until count) {
        list.add(dropStack.copy())
      }
    }
  }

  /** Changes harvest drops from broken blocks
   *
   *  @param event The harvest drop event
   */
  @SubscribeEvent
  def onHarvestDrops(event: HarvestDropsEvent): Unit = {
    val harvester = event.getHarvester
    if (harvester != null && !event.isSilkTouching) {
      val dropStack = getDropStack(event);
      if (dropStack != null) {
        //Debug.chat(harvester.experienceLevel.toString, harvester)
        val fortune = event.getFortuneLevel
        val list = event.getDrops
        val level = harvester.experienceLevel
          modifyHarvestDrops(level, fortune, dropStack, list)
      }
    }
  }
}
