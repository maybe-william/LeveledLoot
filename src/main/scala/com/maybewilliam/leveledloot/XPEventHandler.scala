/*
This file is for the changing xp block drops through event handlers
 */
package com.maybewilliam.leveledloot

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.BlockEvent.BreakEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.apache.logging.log4j.Logger

/** An event handler for block xp drops
 *
 *  @constructor create a new handler
 */
class XPEventHandler
{

  /** Changes xp drops from broken blocks
   *
   *  @param event The block break event
   */
  @SubscribeEvent
  def onBlockBreak(event: BreakEvent): Unit = {
    event.setExpToDrop(event.getExpToDrop + 1)
  }
}

