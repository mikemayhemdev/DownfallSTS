package guardian.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.monsters.exordium.TheGuardian;
import guardian.GuardianMod;
import guardian.characters.GuardianCharacter;

@SpirePatch(clz= OrbWalker.class,method="takeTurn")
public class GuardianTalkOrbwalker {
    private static final EventStrings eventStrings;
    public static final String[] DESCRIPTIONS;

    public static void Prefix(OrbWalker sb) {
        if (AbstractDungeon.player instanceof GuardianCharacter && !GuardianMod.talked5) {

                AbstractDungeon.actionManager.addToBottom(new ShoutAction(sb, DESCRIPTIONS[7], 1.0F, 2.0F));

            GuardianMod.talked5 = true;
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Guardian:GuardianTalk");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }
}