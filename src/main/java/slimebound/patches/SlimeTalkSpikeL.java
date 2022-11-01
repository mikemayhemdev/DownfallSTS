package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_L;
import downfall.downfallMod;
import slimebound.SlimeboundMod;

@SpirePatch(clz = SpikeSlime_L.class, method = "takeTurn")
public class SlimeTalkSpikeL {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:SlimeTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(SpikeSlime_L sb) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.SLIMEBOUND) && !SlimeboundMod.slimeTalkedSpikeL) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(sb, DESCRIPTIONS[8], 1.0F, 2.0F));
            SlimeboundMod.slimeTalkedSpikeL = true;
        }
    }
}