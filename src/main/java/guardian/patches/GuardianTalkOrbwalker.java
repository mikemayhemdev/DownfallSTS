package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

@SpirePatch(clz = OrbWalker.class, method = "takeTurn")
public class GuardianTalkOrbwalker {
    public static final String[] DESCRIPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Guardian:GuardianTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }

    public static void Prefix(OrbWalker sb) {
        if (AbstractDungeon.player instanceof GuardianCharacter && !GuardianMod.talked5) {

            AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[7], 1.0F, 2.0F));

            GuardianMod.talked5 = true;
        }
    }
}