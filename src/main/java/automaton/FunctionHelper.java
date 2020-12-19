package automaton;

import automaton.cardmods.CardEffectsCardMod;
import automaton.cardmods.EncodeMod;
import automaton.cards.AbstractBronzeCard;
import automaton.cards.ForceShield;
import automaton.cards.FunctionCard;
import automaton.powers.*;
import automaton.relics.BronzeBoon;
import automaton.util.TextureLoader;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FunctionHelper {
    public static CardGroup held;
    public static int max = 3;

    public static final float SEQUENCED_CARD_SIZE = 0.225f;
    public static final float FUNC_CARD_SIZE = 0.45f;

    public static final float BG_X = 150f;
    public static final float BG_Y = 700f;
    public static final float HEIGHT_SEQUENCE = 768f;
    public static final float HEIGHT_FUNCTION = 820f;

    public static final Vector2[] cardPositions = {
            new Vector2(208f, HEIGHT_SEQUENCE),
            new Vector2(283f, HEIGHT_SEQUENCE),
            new Vector2(358f, HEIGHT_SEQUENCE),
            new Vector2(433f, HEIGHT_SEQUENCE)
    };

    public static final Vector2[] funcPositions = {
            new Vector2(480f, HEIGHT_FUNCTION),
            new Vector2(580f, HEIGHT_FUNCTION)
    };

    public static final Texture bg = TextureLoader.getTexture("bronzeResources/images/ui/sequenceframe.png");

    public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))"; //Magic code from madness land of RegEx.

    public static boolean doStuff = false;

    public static void init() {
        held = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        secretStorage = makeFunction(false);
        doStuff = true;
    }

    public static void setImportantInfo(AbstractCard q) {
        AbstractCard.CardTarget highestTarget = AbstractCard.CardTarget.ALL;
        for (AbstractCard c : held.group) {
            if (c.target == AbstractCard.CardTarget.SELF_AND_ENEMY || c.target == AbstractCard.CardTarget.ENEMY) {
                if (highestTarget != AbstractCard.CardTarget.SELF_AND_ENEMY) {
                    highestTarget = AbstractCard.CardTarget.SELF_AND_ENEMY;
                }
            }
        }
        q.target = highestTarget;

        AbstractCard.CardType highestType = AbstractCard.CardType.SKILL;
        for (AbstractCard c : held.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                if (highestType != AbstractCard.CardType.ATTACK) {
                    highestType = AbstractCard.CardType.ATTACK;
                }
            }
        }
        q.type = highestType;

        if (q instanceof CustomCard) {
            if (q.type == AbstractCard.CardType.SKILL) {
                ((CustomCard) q).setBackgroundTexture("bronzeResources/images/512/bg_skill_function.png", "bronzeResources/images/1024/bg_skill_function.png");
            } else {
                ((CustomCard) q).setBackgroundTexture("bronzeResources/images/512/bg_attack_function.png", "bronzeResources/images/1024/bg_attack_function.png");
            }
        }
    }

    public static void addToSequence(AbstractCard c) {
        if (CardModifierManager.hasModifier(c, EncodeMod.ID)) {
            CardModifierManager.removeModifiersById(c, EncodeMod.ID, true);
        }
        c.stopGlowing();
        c.resetAttributes();
        c.targetDrawScale = SEQUENCED_CARD_SIZE;
        c.target_x = cardPositions[held.size()].x;
        c.target_y = cardPositions[held.size()].y;
        if (c instanceof AbstractBronzeCard) {
            ((AbstractBronzeCard) c).onInput();
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
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof PreCardCompileEffectsPower) {
                ((PreCardCompileEffectsPower) p).receivePreCardCompileEffects(forGameplay);
            }
        }
        for (AbstractCard c : held.group) {
            if (c instanceof AbstractBronzeCard) {
                if (((AbstractBronzeCard) c).doSpecialCompileStuff) {
                    ((AbstractBronzeCard) c).onCompile(q, forGameplay);
                } else {
                    CardModifierManager.addModifier(q, new CardEffectsCardMod(c));
                }
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
        setImportantInfo(q);
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
            if (c instanceof AbstractBronzeCard && c.hasTag(AutomatonMod.MODIFIES_OUTPUT)) {
                regularOutput = ((AbstractBronzeCard) c).onOutput();
            }
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnOutputFunctionPower) {
                regularOutput = ((OnOutputFunctionPower) p).receiveOutputFunction();
            }
        }
        if (regularOutput) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeFunction(true)));
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    held.clear();
                    secretStorage = makeFunction(false);
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
        sb.setColor(Color.WHITE.cpy());
        sb.draw(bg, BG_X, BG_Y);
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
            float x = (max == 3 ? funcPositions[0].x : funcPositions[1].x);
            float y = (max == 3 ? funcPositions[0].y : funcPositions[1].y);
            secretStorage.current_x = x;
            secretStorage.current_y = y;
            secretStorage.targetDrawScale = FUNC_CARD_SIZE;
            secretStorage.drawScale = FUNC_CARD_SIZE;
        }
    }
}
