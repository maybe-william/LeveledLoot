package com.maybewilliam.leveledloot

import net.minecraft.command.ICommandSender
import net.minecraft.util.text.TextComponentString
import net.minecraft.entity.player.EntityPlayer

object Debug
{
  def chat(str: String, plr: ICommandSender): Unit = {
    if (plr.isInstanceOf[EntityPlayer]) {
      plr.sendMessage(new TextComponentString(str))
    }
  }
}
