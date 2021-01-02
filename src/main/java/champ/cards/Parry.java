package champ.cards;

import champ.powers.CounterPower;
import champ.powers.ParryPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Parry extends AbstractChampCard {

    public final static String ID = makeID("Parry");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 10;
    private static final int UPG_MAGIC = 4;

    public Parry() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        AbstractCard c = new Riposte();
        c.rawDescription = UPGRADE_DESCRIPTION;
        c.initializeDescription();
        cardsToPreview = c;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CounterPower(magicNumber));
        applyToSelf(new ParryPower(1));
    }

    public void upp() {
        upgradeMagicNumber(6);
    }
}