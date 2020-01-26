package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_L;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_S;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz= SpikeSlime_L.class,method="takeTurn")
public class SlimeTalkSpikeL {
    private static final EventStrings eventStrings;
    public static final String[] DESCRIPTIONS;

    public static void Prefix(SpikeSlime_L sb) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter && SlimeboundMod.slimeTalkedSpikeL == false) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(sb, DESCRIPTIONS[8], 1.0F, 2.0F));
            SlimeboundMod.slimeTalkedSpikeL = true;
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:SlimeTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }
}