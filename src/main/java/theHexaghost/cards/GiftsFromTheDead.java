package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.GiftsFromTheDeadPower;
import theHexaghost.powers.GiftsFromTheDeadPowerPlus;

import static automaton.AutomatonMod.makeBetaCardPath;

public class GiftsFromTheDead extends AbstractHexaCard {

    public final static String ID = makeID("GiftsFromTheDead");

    //stupid intellij stuff POWER, SELF, RARE

    public GiftsFromTheDead() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = baseMagicNumber = 3;
        HexaMod.loadJokeCardImage(this, "GiftsFromTheDead.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GiftsFromTheDeadPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.isInnate = true;
            upgradeBaseCost(1);
        }
    }
}