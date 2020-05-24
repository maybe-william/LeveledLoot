package com.maybewilliam.leveledloot

object References
{
  final val MODID = "leveledloot"
  final val NAME = "LeveledLoot"
  final val VERSION = "1.0"
  final val ACCEPTED = "[1.12.2]"

  //@SubscribeEvent
  //def onLootTablesLoaded(event: LootTableLoadEvent): Unit = {
    //if (event.getName().equals(new ResourceLocation("minecraft","loot_tables/blocks/stone"))) {
      //var cond = new LootCondition() {
        //def testCondition(x: Random, y: LootContext): Boolean = {
          //return true;
        //}
      //}
      //var func = new LootFunction(Array(cond)) {
        //def apply(i: ItemStack, r: Random, context: LootContext): ItemStack = {
          //return new ItemStack(Items.FEATHER, 3);
        //}
      //}//, new RandomValueRange(1, 5))) {}
      //var x = event.getTable();
      //var y = x.getPool("main")
      //both loot function and loot condition are abstract. using syntax [] and [0]
      //var z = y.addEntry(new LootEntryItem(Items.FEATHER, 10, 0, Array(func), Array(cond), "minecraft:feather"));
    //}
  //}
}
