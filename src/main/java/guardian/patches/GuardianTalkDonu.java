package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.beyond.Donu;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

@SpirePatch(clz = Donu.class, method = "takeTurn")
public class GuardianTalkDonu {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Guardian:GuardianTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(Donu sb) {
        if (AbstractDungeon.player instanceof GuardianCharacter && !GuardianMod.talked4) {

            AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[5], 1.0F, 2.0F));

            GuardianMod.talked4 = true;
        }
    }
}