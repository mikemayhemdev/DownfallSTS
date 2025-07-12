package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import collector.powers.AddCopyNextTurnPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;
import static collector.util.Wiz.applyToSelfTop;


public class Hymn extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Hymn.class.getSimpleName());

    public Hymn() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(Hymn.class.getSimpleName() + ".png"));
        baseBlock = 6;
        this.tags.add(AwakenedOneMod.CHANT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (isTrig_chant()) {
            atb(new MultiGroupSelectAction(
                    "",
                    (cards, groups) -> {
                        Collections.reverse(cards);
                        cards.forEach(c -> att(new AbstractGameAction() {
                            public void update() {
                                isDone = true;
                                addToBot(new ExhaustSpecificCardAction(c, p.discardPile));
                                applyToSelfTop(new AddCopyNextTurnPower(c));
                            }
                        }));
                    },
                    1, false, c -> c instanceof AbstractCard, CardGroup.CardGroupType.DISCARD_PILE));
            chant();
        }
    }

    @Override
    public void chant() {
        checkOnChant();
    }


    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}