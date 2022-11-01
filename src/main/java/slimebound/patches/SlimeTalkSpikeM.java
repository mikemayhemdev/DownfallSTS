package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_M;
import downfall.downfallMod;
import slimebound.SlimeboundMod;

@SpirePatch(clz = SpikeSlime_M.class, method = "takeTurn")
public class SlimeTalkSpikeM {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:SlimeTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(SpikeSlime_M sb) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.SLIMEBOUND) && !SlimeboundMod.slimeTalkedSpikeM) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(sb, DESCRIPTIONS[9], 1.0F, 2.0F));
            SlimeboundMod.slimeTalkedSpikeM = true;
        }
    }
}