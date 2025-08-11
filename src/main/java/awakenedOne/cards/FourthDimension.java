package awakenedOne.cards;

import awakenedOne.powers.FourthDimensionPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.util.Wiz.*;

public class FourthDimension extends AbstractAwakenedCard {
    public final static String ID = makeID(FourthDimension.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public FourthDimension() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 3;
        //this.tags.add(CardTags.HEALING);
        loadJokeCardImage(this, makeBetaCardPath(FourthDimension.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (AbstractCard c : cards) {
                applyToSelfTop(new FourthDimensionPower(magicNumber, c.makeStatEquivalentCopy()));
                att(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            }
        }));

    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}