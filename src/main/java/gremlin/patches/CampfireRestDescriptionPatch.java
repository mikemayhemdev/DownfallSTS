package gremlin.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.NightTerrors;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.RegalPillow;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import gremlin.characters.GremlinCharacter;

@SpirePatch(clz = RestOption.class, method = SpirePatch.CONSTRUCTOR)
public class CampfireRestDescriptionPatch {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    @SpireInsertPatch(
            rloc=27,
            localvars={"healAmt"}
    )
    public static void Insert(RestOption __instance, boolean active, int healAmt){
        if(AbstractDungeon.player instanceof GremlinCharacter){
            String desc;
            if (ModHelper.isModEnabled(NightTerrors.ID))
            {
                desc = (TEXT[1] + healAmt + ").");
                if (AbstractDungeon.player.hasRelic(RegalPillow.ID)) {
                    desc = (desc + "\n+15" + TEXT[2] + AbstractDungeon.player.getRelic(RegalPillow.ID).name + LocalizedStrings.PERIOD);
                }
            }
            else
            {
                desc = (TEXT[3] + healAmt + ").");
                if (AbstractDungeon.player.hasRelic(RegalPillow.ID)) {
                    desc = (desc + "\n+15" + TEXT[2] + AbstractDungeon.player.getRelic(RegalPillow.ID).name + LocalizedStrings.PERIOD);
                }
            }
            desc = desc + "\n" + TEXT[4];
            ReflectionHacks.setPrivateInherited(__instance, RestOption.class, "description", desc);
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Gremlin:RestOption");
        TEXT = CampfireRestDescriptionPatch.uiStrings.TEXT;
    }
}
