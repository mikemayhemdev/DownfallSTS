package gremlin.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import gremlin.actions.GremlinSwapAction;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;
import gremlin.relics.ShortStature;
import javassist.CtBehavior;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="damage",
        paramtypez={DamageInfo.class}
)
public class PlayerDamagePatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static SpireReturn Insert(AbstractPlayer __instance, DamageInfo info) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (__instance instanceof GremlinCharacter) {
                if (!__instance.hasRelic("Mark of the Bloom")) {
                    if (__instance.hasRelic(ShortStature.ID) && __instance.getRelic(ShortStature.ID).counter == -1) {
                        __instance.currentHealth = 0;
                        __instance.getRelic(ShortStature.ID).onTrigger();
                        return SpireReturn.Return(null);
                    }
                }

                ((GremlinCharacter) __instance).gremlinDeathSFX();
                boolean anotherGrem = false;
                for(int i=0;i<AbstractDungeon.player.maxOrbs;i++) {
                    if (AbstractDungeon.player.orbs.get(i) instanceof GremlinStandby){
                        anotherGrem = true;
                        break;
                    }
                }
                if(anotherGrem) {
                    __instance.currentHealth = 0;
                    (new GremlinSwapAction()).update();
                    return SpireReturn.Return(null);
                }
            }
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "isDead");

            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}
