package collector.cards.collectibles;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theHexaghost.powers.BurnPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.getEnemies;

public class NemesisCard extends AbstractCollectibleCard {
    public final static String ID = makeID(NemesisCard.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , , 

    public NemesisCard() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IntangiblePlayerPower(p, 1));
        // I am NOT "synergizing" this with the Red Candle.
        applyToSelf(new BurnPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(2);
        upgradeMagicNumber(-4);
    }
}