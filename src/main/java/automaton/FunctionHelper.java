package automaton;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cardmods.ExhaustCardMod;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.FunctionCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;

import java.util.ArrayList;

public class FunctionHelper {
    public static CardGroup held;
    public static int max = 4;

    public static boolean doStuff = false;

    public static void init() {
        held = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        secretStorage = makeFunction();
        doStuff = true;
    }

    public static void addToSequence(AbstractCard c) {
        held.addToTop(c);
        if (held.size() == max) {
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(makeFunction()));
            held.clear();
        }
        secretStorage = makeFunction();
    }

    public static AbstractCard secretStorage = null;

    public static AbstractCard makeFunction() {
        AbstractCard q = new FunctionCard();
        for (AbstractCard c : held.group) {
            if (c instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) c).onCompile(q);
            } else {
                CardModifierManager.addModifier(q, new CardEffectsCardMod(c));
            }
        }
        q.modifyCostForCombat(-1);
        System.out.println(q.rawDescription);
        return q;
    }

    public static void render(SpriteBatch sb) {
        for (AbstractCard c : held.group) {
            c.render(sb);
        }
        if (secretStorage != null) {
            secretStorage.render(sb);
        }
    }

    public static void update() {
        for (AbstractCard c : held.group) {
            c.update();
            /*
            if (c.hb.hovered) { // Guardian orbs check against hovering the orb. Hovering the card might be weird, so we'd need static hitboxes, which I'm lazy to set up now
                c.targetDrawScale = 1F;
            } else {
                c.targetDrawScale = GuardianMod.stasisCardRenderScale;
            }
            */
        }
        if (secretStorage != null) {
            secretStorage.update();
            float x = (400 + (200 * (max + 1))) * Settings.scale;
            float y = 800 * Settings.scale;
            secretStorage.current_x = x;
            secretStorage.current_y = y;
        }
    }

    //If everything is a cardmod, things can be done mostly dynamically.
    //Damages-card-mod, blocks-card-mod, etc.
    //The main issue would be being too dynamic.
    //"Apply 1 Vulnerable. NL Deal 10 damage." isn't any good.
    //The alternative is using similar tech to RepeatCardAction to make the FunctionCard silent-play held1, held2, held3, held4, utilizing their Compile bonuses correctly.
    //That's probably cleaner, and makes stuff like Vuln -> Damage clearer, if it has to happen.
    //Hmmm.
}
