package gremlin.patches.relicpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Waffle;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= Waffle.class,
        method="onEquip"
)
public class WafflePatch {
    public static void Postfix(Waffle __instance) {
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter)AbstractDungeon.player).healGremlins(AbstractDungeon.player.maxHealth);
        }
    }
}
