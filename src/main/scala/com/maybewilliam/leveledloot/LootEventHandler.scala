/*
This file is for the changing of loot through event handlers
 */
package com.maybewilliam.leveledloot

import java.util.List
import java.lang.Math
import java.util.Random
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraft.util.EntityDamageSource
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingDropsEvent
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.apache.logging.log4j.Logger

/** An event handler for block loot drops
 *
 *  @constructor create a new handler
 */
class BlockLootEventHandler
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
   *  @param fortuneLevel The level of fortune enchantment used
   *  @param dropStack The ItemStack to drop
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
      val dropStack = this.getDropStack(event);
      if (dropStack != null) {
        //Debug.chat(harvester.experienceLevel.toString, harvester)
        val fortune = event.getFortuneLevel
        val list = event.getDrops
        val level = harvester.experienceLevel
        this.modifyHarvestDrops(level, fortune, dropStack, list)
      }
    }
  }
}




/** An event handler for entity loot drops
 *
 *  @constructor Create a new handler
 */
class EntityLootEventHandler
{
  val rand = new Random
  var livingEvent: LivingDropsEvent = null

  /** Copies an EntityItem in the same world and location
   *
   *  @param items the EntityItem to make a copy of
   *
   *  @returns a new duplicate of the EntityItem
   */
  def copyItems(items: EntityItem): EntityItem = {
    val item = items.getItem().copy()
    val world = livingEvent.getEntityLiving.getEntityWorld
    val x = livingEvent.getEntityLiving.getPosition.getX
    val y = livingEvent.getEntityLiving.getPosition.getY
    val z = livingEvent.getEntityLiving.getPosition.getZ
    return new EntityItem(world, x, y, z, item)
  }

  /** Returns a drop stack if drop exists and isn't a player, otherwise null
   *
   *  @param event The HarvestDropsEvent
   *
   *  @returns The stack to return or null
   */
  def getDropStack(event: LivingDropsEvent): EntityItem = {

    val num = event.getDrops.size
    if(num == 0 || event.getEntity.isInstanceOf[EntityPlayer]) {
      return null
    }

    val stack = event.getDrops.get(0)

    return stack
  }

  /** Changes entity drops
   *
   *  @param level The block-breaker's level
   *  @param lootingLevel The level of looting enchantment used
   *  @param dropStack The EntityItem to drop
   *  @param list The list of drops
   */
  def modifyLivingDrops(level: Int, lootingLevel: Int, dropStack: EntityItem, list: List[EntityItem]): Unit = {

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

    //looting enchantment calculation applied to amount
    if (lootingLevel == 1) {
      if (rand.nextInt(101) <= 33) {
        count = count * 2
      }
    }
    if (lootingLevel == 2) {
      if (rand.nextInt(101) <= 25) {
        count = count * 2
      }
      if (rand.nextInt(101) <= 25) {
        count = count * 3
      }
    }
    if (lootingLevel == 3) {
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
        list.add(this.copyItems(dropStack))
      }
    }
  }

  /** Changes drops from killed entities
   *
   *  @param event The harvest drop event
   */
  @SubscribeEvent
  def onLivingDrops(event: LivingDropsEvent): Unit = {
    val source = event.getSource
    if (source.isInstanceOf[EntityDamageSource]) {
      val entity = source.getTrueSource
      if (entity != null && entity.isInstanceOf[EntityPlayer]) {
        val entityPlayer = entity.asInstanceOf[EntityPlayer]
        this.livingEvent = event
        val dropStack = this.getDropStack(event);
        if (dropStack != null) {
          //Debug.chat(entityPlayer.experienceLevel.toString, entityPlayer)
          val looting = event.getLootingLevel
          val list = event.getDrops
          val level = entityPlayer.experienceLevel
          this.modifyLivingDrops(level, looting, dropStack, list)
        }
      }
    }
  }
}

