package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.ApplyBurnAtTurnStartOncePower;
import theHexaghost.powers.GiftsFromTheDeadPower;
import theHexaghost.powers.GiftsFromTheDeadPowerPlus;

public class GiftsFromTheDead extends AbstractHexaCard {

    public final static String ID = makeID("GiftsFromTheDead");

    //stupid intellij stuff POWER, SELF, RARE

    public GiftsFromTheDead() {
        super(ID, -2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
        this.magicNumber = baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "It's too hot to touch!";
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        applyToSelf(new GiftsFromTheDeadPower(1));
        if (upgraded) applyToSelf(new GiftsFromTheDeadPowerPlus(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}