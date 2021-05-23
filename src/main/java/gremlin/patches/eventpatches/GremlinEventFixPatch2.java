package gremlin.patches.eventpatches;

import basemod.patches.whatmod.WhatMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.EventRoom;
import gremlin.characters.GremlinCharacter;
import gremlin.patches.GremlinEnum;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = {DamageInfo.class}
)
public class GremlinEventFixPatch2 {
    public static boolean insideDamage = false;
    public static SpireReturn Prefix(AbstractPlayer __instance, DamageInfo param) {
        System.out.println("DAMAGE CHECK: " + insideDamage + " " + param.output);
        if (!insideDamage && AbstractDungeon.player.chosenClass == GremlinEnum.GREMLIN && AbstractDungeon.getCurrRoom() instanceof EventRoom && WhatMod.findModID(AbstractDungeon.getCurrRoom().event.getClass()) != null) {
            insideDamage = true;
            ((GremlinCharacter) AbstractDungeon.player).damageGremlins((param.output + 4) / 5);
            AbstractDungeon.player.currentHealth = AbstractDungeon.player.currentHealth - ((param.output + 4)/ 5);
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}