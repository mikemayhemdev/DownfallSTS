package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import theHexaghost.HexaMod;

public class Floatwork extends AbstractHexaCard {
    public final static String ID = makeID("Floatwork");

    public Floatwork() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        baseBlock = 4;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "Floatwork.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded){
            blck();
        }
        applyToSelf(new PlatedArmorPower(p, magicNumber));
    }

    public void afterlife() {
        applyToSelf(new PlatedArmorPower(AbstractDungeon.player, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();

        }
    }
}