package expansioncontent.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.BeatOfDeathThatDoesntKillYouPower;

public class BeatOfDeath extends AbstractExpansionCard {
    public static final String ID = makeID("BeatOfDeath");

    private static final int MAGIC = 2;

    public BeatOfDeath() {
        super(ID, 2, AbstractCard.CardType.POWER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        setBackgroundTexture("expansioncontentResources/images/512/bg_boss_heart.png", "expansioncontentResources/images/1024/bg_boss_heart.png");
        this.magicNumber = this.baseMagicNumber = 2;
        expansionContentMod.loadJokeCardImage((AbstractCard)this, "BeatOfDeath.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf((AbstractPower)new BeatOfDeathThatDoesntKillYouPower((AbstractCreature)p, this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
