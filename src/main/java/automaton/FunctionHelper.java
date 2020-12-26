package automaton;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.ForceShield;
import automaton.cards.FunctionCard;
import automaton.powers.*;
import automaton.relics.OnCompileRelic;
import automaton.util.TextureLoader;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.HashMap;

public class FunctionHelper {
    public static CardGroup held;
    public static int max = 3;

    public static boolean doExtraNonSpecificCopy = false;

    public static HashMap<CardEffectsCardMod, AbstractCard> cardModsInfo = new HashMap<>();

    public static final float SEQUENCED_CARD_SIZE = 0.225f;
    public static final float FUNC_CARD_SIZE = 0.45f;

    public static final float BG_X = 150f;
    public static final float BG_Y = 700f;
    public static final float HEIGHT_SEQUENCE = 768f;
    public static final float HEIGHT_FUNCTION = 820f;

    public static final Vector2[] cardPositions = {
            new Vector2(210f, HEIGHT_SEQUENCE),
            new Vector2(285f, HEIGHT_SEQUENCE),
            new Vector2(360f, HEIGHT_SEQUENCE),
            new Vector2(435f, HEIGHT_SEQUENCE)
    };

    public static final Vector2[] funcPositions = {
            new Vector2(480f, HEIGHT_FUNCTION),
            new Vector2(580f, HEIGHT_FUNCTION)
    };

    public static final Texture bg = TextureLoader.getTexture("bronzeResources/images/ui/sequenceframe.png");
    public static final Texture bg_4card = TextureLoader.getTexture("bronzeResources/images/ui/sequenceframe4cards.png");

    public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))"; //Magic code from madness land of RegEx.

    public static boolean doStuff = false;

    public static void init() {
        held = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        secretStorage = makeFunction(false);
        doStuff = true;
    }

    public static void applyPowers() {
        secretStorage = makeFunction(false);
    }

    public static void addToSequence(AbstractCard c) {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnAddToFuncPower) {
                ((OnAddToFuncPower) p).receiveAddToFunc(c); // Optimize
            }
        }
        c.stopGlowing();
        c.resetAttributes();
        c.targetDrawScale = SEQUENCED_CARD_SIZE;
        c.target_x = cardPositions[held.size()].x;
        c.target_y = cardPositions[held.size()].y;
        held.addToTop(c);
        if (c instanceof AbstractBronzeCard) {
            ((AbstractBronzeCard) c).position = ((AbstractBronzeCard) c).getSequencePosition();
        }
        if (held.size() == max) {
            output();
        }
        secretStorage = makeFunction(false);
        for (AbstractPower q : AbstractDungeon.player.powers) {
            if (q instanceof PostAddToFuncPower) {
                ((PostAddToFuncPower) q).receivePostAddToFunc(c); // Cloning Beam
            }
        }
    }

    public static AbstractCard secretStorage = null;

    public static AbstractCard makeFunction(boolean forGameplay) {
        AbstractBronzeCard function = new FunctionCard();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof PreCardCompileEffectsPower) {
                ((PreCardCompileEffectsPower) p).receivePreCardCompileEffects(forGameplay); // Clean Code
            }
        }
        for (AbstractCard c : held.group) {
            if (c instanceof AbstractBronzeCard) {
                if (((AbstractBronzeCard) c).doSpecialCompileStuff) {
                    ((AbstractBronzeCard) c).onCompilePreCardEffectEmbed(forGameplay); // Terminator, Constructor, Chosen Strike
                }
            }
        }
        int counter = 0;
        boolean justDoNoun = false;
        boolean foundExactlyOne = false;
        for (AbstractCard u : held.group) {
            if (!u.hasTag(AutomatonMod.NO_TEXT)) {
                justDoNoun = !foundExactlyOne;
                foundExactlyOne = true;
            }
        }
        for (AbstractCard c : held.group) { // Apply the card effect mods
            if (c.hasTag(AutomatonMod.NO_TEXT)) {
                CardModifierManager.addModifier(function, new CardEffectsCardMod(c, -99));
            } else {
                if (justDoNoun) {
                    CardModifierManager.addModifier(function, new CardEffectsCardMod(c, 1));
                } else {
                    CardModifierManager.addModifier(function, new CardEffectsCardMod(c, counter));
                    counter += 1;
                }
            }
        }
        for (AbstractCard c : held.group) {
            if (c instanceof AbstractBronzeCard) {
                if (((AbstractBronzeCard) c).doSpecialCompileStuff) {
                    ((AbstractBronzeCard) c).onCompile(function, forGameplay); // Compile effects
                }
            }
        }
        for (AbstractCard c : held.group) {
            if (c instanceof AbstractBronzeCard) {
                if (((AbstractBronzeCard) c).doSpecialCompileStuff) {
                    ((AbstractBronzeCard) c).onCompileLast(function, forGameplay); // Cost modifying compile effects
                }
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnCompilePower) {
                ((OnCompilePower) p).receiveCompile(function, forGameplay); // Free Function Potion, Infinite Beams & Hardened Form
            }
        }
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof OnCompileRelic) {
                ((OnCompileRelic) r).receiveCompile(function, forGameplay); // Bronze Core, etc
            }
        }
        return function;
    }


    public static void output() {
        ForceShield.decrementShields(); // Decrease cost of Force Shields
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnOutputFunctionPower) {
                ((OnOutputFunctionPower) p).receiveOutputFunction(); // Hardcode
            }
        }
        if (doExtraNonSpecificCopy) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeFunction(true))); // Duplicate Function potion
            doExtraNonSpecificCopy = false;
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeFunction(true))); // Regular output to hand
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() { // Clears and resets Function Helper -- this part being bound to Action Queue makes some weird stuff.
                held.clear();
                secretStorage = makeFunction(false);
                isDone = true;
            }
        });
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AfterOutputFunctionPower) {
                ((AfterOutputFunctionPower) p).receiveAfterOutputFunction(); // Class Default
            }
        }
    }

    public static void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        if (max == 4) {
            sb.draw(bg_4card, BG_X, BG_Y);
        } else {
            sb.draw(bg, BG_X, BG_Y);
        }
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
            c.updateHoverLogic();
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
            secretStorage.updateHoverLogic();
            float x = (max == 3 ? funcPositions[0].x : funcPositions[1].x);
            float y = (max == 3 ? funcPositions[0].y : funcPositions[1].y);
            secretStorage.target_x = x;
            secretStorage.current_x = x;
            secretStorage.target_y = y;
            secretStorage.current_y = y;
            secretStorage.targetDrawScale = FUNC_CARD_SIZE;
            secretStorage.drawScale = FUNC_CARD_SIZE;
        }
    }
}
