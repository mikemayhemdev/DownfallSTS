package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

@SpirePatch(clz = Sentry.class, method = "takeTurn")
public class GuardianTalkSentries {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Guardian:GuardianTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(Sentry sb) {
        if (AbstractDungeon.player instanceof GuardianCharacter) {
            if (!GuardianMod.talked7 && !GuardianMod.talked8 && !GuardianMod.talked9) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[1], 1.0F, 2.0F));
                GuardianMod.talked7 = true;
            } else if (!GuardianMod.talked8 && !GuardianMod.talked9) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[2], 1.0F, 2.0F));
                GuardianMod.talked8 = true;
            } else if (!GuardianMod.talked9) {
                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[3], 1.0F, 2.0F));
                GuardianMod.talked9 = true;

            }
        }
    }
}