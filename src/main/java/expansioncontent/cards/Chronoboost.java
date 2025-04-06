package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.ChronoBoostPower;

public class Chronoboost extends AbstractExpansionCard {
    public static final String ID = makeID("Chronoboost");

    private static final int MAGIC = 2;

    public Chronoboost() {
        super(ID, 1, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(expansionContentMod.STUDY_TIMEEATER);
        this.tags.add(expansionContentMod.STUDY);
        this.baseMagicNumber = this.magicNumber = 2;
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_timeeater.png", "expansioncontentResources/images/1024/bg_boss_timeeater.png");
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "Chronoboost.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new ChronoBoostPower((AbstractCreature)p, (AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
