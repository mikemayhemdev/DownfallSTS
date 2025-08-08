package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class DesperatePrayer extends AbstractAwakenedCard {
    public final static String ID = makeID(DesperatePrayer.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public DesperatePrayer() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(DesperatePrayer.class.getSimpleName() + ".png"));
        this.tags.add(AwakenedOneMod.CHANT);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
        AbstractCard d = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
        AbstractCard e = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
        if (isTrig_chant()) {
            c.setCostForTurn(0);
            d.setCostForTurn(0);
            e.setCostForTurn(0);
            chant();
        }
        this.addToBot(new MakeTempCardInHandAction(c, true));
        this.addToBot(new MakeTempCardInHandAction(d, true));
        this.addToBot(new MakeTempCardInHandAction(e, true));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void chant() {
        checkOnChant();
    }

    public void upp() {
        upgradeBaseCost(1);

    }
}