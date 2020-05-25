/*
This file is for any assorted debugging functions
 */
package com.maybewilliam.leveledloot

import net.minecraft.command.ICommandSender
import net.minecraft.util.text.TextComponentString
import net.minecraft.entity.player.EntityPlayer

/** This object houses the debugging functions */
object Debug
{
  /** send a string of text to a player in the ingame chat
   *
   *  @param str The string to send
   *  @param plr The player to send it to
   */
  def chat(str: String, plr: ICommandSender): Unit = {
    if (plr.isInstanceOf[EntityPlayer]) {
      plr.sendMessage(new TextComponentString(str))
    }
  }
}
