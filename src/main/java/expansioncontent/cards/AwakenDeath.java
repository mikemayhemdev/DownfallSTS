package expansioncontent.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import expansioncontent.expansionContentMod;

public class AwakenDeath extends AbstractExpansionCard {
    public static final String ID = makeID("AwakenDeath");

    public AwakenDeath() {
        super(ID, 2, AbstractCard.CardType.POWER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.tags.add(expansionContentMod.STUDY);
        this.tags.add(expansionContentMod.STUDY_AWAKENEDONE);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_awakenedone.png", "expansioncontentResources/images/1024/bg_boss_awakenedone.png");
        this.baseMagicNumber = this.magicNumber = 1;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "AwakenDeath.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new CuriosityPower((AbstractCreature)p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
