package automaton;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.ForceShield;
import automaton.cards.FunctionCard;
import automaton.powers.AfterOutputFunctionPower;
import automaton.powers.CloningPower;
import automaton.powers.OnCompilePower;
import automaton.relics.BronzeBoon;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FunctionHelper {
    public static CardGroup held;
    public static int max = 4;

    public static int funcsThisCombat = 0;

    public static boolean doStuff = false;

    public static void init() {
        held = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        secretStorage = makeFunction(false);
        doStuff = true;
        funcsThisCombat = 0;
    }

    public static void addToSequence(AbstractCard c) {
        c.stopGlowing();
        for (AbstractCard q : held.group) {
            if (q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).onInput();
            }
        }
        held.addToTop(c);
        for (AbstractCard q : held.group) {
            if (q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).fullLen = held.size();
            }
        }
        if (held.size() == max) {
            output();
        }
        secretStorage = makeFunction(false);
        if (AbstractDungeon.player.hasPower(CloningPower.POWER_ID)) {
            AbstractDungeon.player.getPower(CloningPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    FunctionHelper.addToSequence(c.makeStatEquivalentCopy());
                }
            });
        }
    }

    public static AbstractCard secretStorage = null;

    public static AbstractCard makeFunction(boolean forGameplay) {
        AbstractCard q = new FunctionCard();
        for (AbstractCard c : held.group) {
            if (c instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) c).onCompile(q, forGameplay);
            } else {
                CardModifierManager.addModifier(q, new CardEffectsCardMod(c));
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnCompilePower) {
                ((OnCompilePower) p).receiveCompile(q, forGameplay);
            }
        }
        if (q.cost > 0 && AbstractDungeon.player.hasRelic(BronzeBoon.ID)) {
            q.cost -= 1;
            q.costForTurn -= 1;
        }
        System.out.println(q.rawDescription);
        return q;
    }

    public static void output() {
        for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
            if (q instanceof ForceShield) {
                q.updateCost(-1);
            }
        }
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q instanceof ForceShield) {
                q.updateCost(-1);
            }
        }
        for (AbstractCard q : AbstractDungeon.player.discardPile.group) {
            if (q instanceof ForceShield) {
                q.updateCost(-1);
            }
        }
        boolean regularOutput = true;
        for (AbstractCard c : held.group) {
            if (c instanceof AbstractBronzeCard) {
                regularOutput = ((AbstractBronzeCard) c).onOutput();
            }
        }
        if (regularOutput) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeFunction(true)));
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    held.clear();
                    isDone = true;
                }
            });
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AfterOutputFunctionPower) {
                ((AfterOutputFunctionPower) p).receiveAfterOutputFunction();
            }
        }
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
