package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_S;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz=AcidSlime_S.class,method="takeTurn")
public class SlimeTalkAcidS {
    private static final EventStrings eventStrings;
    public static final String[] DESCRIPTIONS;

    public static void Prefix(AcidSlime_S sb) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter && SlimeboundMod.slimeTalkedAcidS == false) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(sb, DESCRIPTIONS[3], 1.0F, 2.0F));
            SlimeboundMod.slimeTalkedAcidS = true;
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:SlimeTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }
}