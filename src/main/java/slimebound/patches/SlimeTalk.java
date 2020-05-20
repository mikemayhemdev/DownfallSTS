package slimebound.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import expansioncontent.relics.StudyCardRelic;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;

@SpirePatch(clz = SlimeBoss.class, method = "takeTurn")
public class SlimeTalk {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Slimebound:SlimeTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(SlimeBoss sb) {
        if (AbstractDungeon.player instanceof SlimeboundCharacter && !SlimeboundMod.slimeTalked) {
            if (AbstractDungeon.player.hasRelic(StudyCardRelic.ID)) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[11], 1.0F, 2.0F));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[0], 1.0F, 2.0F));
            }
            SlimeboundMod.slimeTalked = true;
        }
    }
}