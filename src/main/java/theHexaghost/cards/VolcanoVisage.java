package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import sneckomod.SneckoMod;
import theHexaghost.powers.VolcanoVisagePower;
import theHexaghost.relics.CandleOfCauterizing;

public class VolcanoVisage extends AbstractHexaCard {

    public final static String ID = makeID("VolcanoVisage");

    public VolcanoVisage() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        tags.add(HexaMod.GHOSTWHEELCARD);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "VolcanoVisage.png");
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
        applyToSelf(new VolcanoVisagePower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}