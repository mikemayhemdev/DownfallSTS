package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_S;
import downfall.downfallMod;
import slimebound.SlimeboundMod;

@SpirePatch(clz = AcidSlime_S.class, method = "takeTurn")
public class SlimeTalkAcidS {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:SlimeTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(AcidSlime_S sb) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.SLIMEBOUND) && !SlimeboundMod.slimeTalkedAcidS) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(sb, DESCRIPTIONS[3], 1.0F, 2.0F));
            SlimeboundMod.slimeTalkedAcidS = true;
        }
    }
}