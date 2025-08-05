package expansioncontent.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.VexVinciblePower;

public class InvincibleStrength extends AbstractExpansionCard {
    public static final String ID = makeID("InvincibleStrength");

    public InvincibleStrength() {
        super(ID, 1, AbstractCard.CardType.POWER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_heart.png", "expansioncontentResources/images/1024/bg_boss_heart.png");
        baseMagicNumber = magicNumber = 10;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "InvincibleStrength.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf((AbstractPower)new VexVinciblePower((AbstractCreature)p, 1, this.magicNumber, this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-5);
        }
    }
}
