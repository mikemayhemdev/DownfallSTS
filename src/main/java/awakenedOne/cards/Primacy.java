package awakenedOne.cards;

import awakenedOne.powers.PrimacyPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class Primacy extends AbstractAwakenedCard {
    public final static String ID = makeID(Primacy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Primacy() {
        super(ID, 8, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 20;
        this.magicNumber = this.baseMagicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(Primacy.class.getSimpleName() + ".png"));
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.configureCostsOnNewCard();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardAction(magicNumber));
    }

    public void configureCostsOnNewCard() {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.POWER) {
                updateCost(-1);
            }
        }
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            this.updateCost(-1);
        }
    }

    public void upp() {
        upgradeBlock(4);
        upgradeMagicNumber(1);
    }
}