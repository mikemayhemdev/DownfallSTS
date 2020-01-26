package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_S;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz=SpikeSlime_S.class,method="takeTurn")
public class SlimeTalkSpikeS {
    private static final EventStrings eventStrings;
    public static final String[] DESCRIPTIONS;

    public static void Prefix(SpikeSlime_S sb) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter && SlimeboundMod.slimeTalkedSpikeS == false) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(sb, DESCRIPTIONS[10], 1.0F, 2.0F));
            SlimeboundMod.slimeTalkedSpikeS = true;
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:SlimeTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }
}