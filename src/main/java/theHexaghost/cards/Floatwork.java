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
        baseBurn = burn = 1;
        baseMagicNumber = magicNumber = 2;
        baseBlock = 2;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "Floatwork.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded){
            blck();
        }
        applyToSelf(new PlatedArmorPower(p, magicNumber+burn));
    }

    public void afterlife() {
        applyToSelf(new PlatedArmorPower(AbstractDungeon.player, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeMagicNumber(1);
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}