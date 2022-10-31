package gremlin.patches.talkpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.GremlinWizard;
import gremlin.characters.GremlinCharacter;

@SpirePatch(
        clz= GremlinWizard.class,
        method="takeTurn"
)
public class GremlinWizardPatch {
    public static void Prefix(GremlinWizard __instance){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter) AbstractDungeon.player).gremlinTalk(__instance);
        }
    }
}