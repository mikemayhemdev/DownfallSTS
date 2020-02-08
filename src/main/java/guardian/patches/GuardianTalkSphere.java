package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.city.SphericGuardian;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

@SpirePatch(clz = SphericGuardian.class, method = "takeTurn")
public class GuardianTalkSphere {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Guardian:GuardianTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(SphericGuardian sb) {
        if (AbstractDungeon.player instanceof GuardianCharacter && !GuardianMod.talked6) {

            AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[8], 1.0F, 2.0F));

            GuardianMod.talked6 = true;
        }
    }
}