package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.CrispyPower_new;
import theHexaghost.relics.CandleOfCauterizing;

public class ExtraCrispy extends AbstractHexaCard {

    public final static String ID = makeID("ExtraCrispy");

    public ExtraCrispy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 12;
        HexaMod.loadJokeCardImage(this, "ExtraCrispy.png");
    }

    @Override
    public void applyPowers() {
        if(AbstractDungeon.player.hasRelic(CandleOfCauterizing.ID)){
            this.magicNumber = this.baseMagicNumber + CandleOfCauterizing.SOULBURN_BONUS_AMOUNT;
        }
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if(AbstractDungeon.player.hasRelic(CandleOfCauterizing.ID)){
            this.magicNumber = this.baseMagicNumber + CandleOfCauterizing.SOULBURN_BONUS_AMOUNT;
        }
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CrispyPower_new(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
        }
    }

}
