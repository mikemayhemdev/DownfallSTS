package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.beyond.Deca;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

@SpirePatch(clz = Deca.class, method = "takeTurn")
public class GuardianTalkDeca {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Guardian:GuardianTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(Deca sb) {
        if (AbstractDungeon.player instanceof GuardianCharacter && !GuardianMod.talked3) {

            AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[6], 1.0F, 2.0F));

            GuardianMod.talked3 = true;
        }
    }
}