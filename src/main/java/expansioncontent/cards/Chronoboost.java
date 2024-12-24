package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.ChronoBoostPower;

import static expansioncontent.expansionContentMod.loadJokeCardImage;


public class Chronoboost extends AbstractExpansionCard {
    public final static String ID = makeID("Chronoboost");

    private static final int MAGIC = 2;

    public Chronoboost() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(expansionContentMod.STUDY_TIMEEATER);
        tags.add(expansionContentMod.STUDY);
        baseMagicNumber = magicNumber = MAGIC;
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_timeeater.png", "expansioncontentResources/images/1024/bg_boss_timeeater.png");
        loadJokeCardImage(this, "Chronoboost.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new ChronoBoostPower(p, p, magicNumber), magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
    upgradeMagicNumber(1);
        }
    }

}


