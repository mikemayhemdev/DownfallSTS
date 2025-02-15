package champ.cards;

import champ.powers.CounterPower;
import champ.powers.ParryPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class Parry extends AbstractChampCard {

    public final static String ID = makeID("Parry");

    public Parry() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        baseMagicNumber = magicNumber = 4;
        AbstractCard c = new Riposte();
        c.rawDescription = UPGRADE_DESCRIPTION;
        c.initializeDescription();
        cardsToPreview = c;
        postInit();
        loadJokeCardImage(this, "Parry.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new CounterPower(magicNumber));
        applyToSelf(new ParryPower(1));
    }

    public void upp() {
        upgradeBlock(4);
        upgradeMagicNumber(2);
    }
}