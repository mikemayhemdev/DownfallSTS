package awakenedOne.cards;

import awakenedOne.powers.SongOfSorrowPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.*;

public class SongOfSorrow extends AbstractAwakenedCard {
    public final static String ID = makeID(SongOfSorrow.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public SongOfSorrow() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 7;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(SongOfSorrow.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SongOfSorrowPower(magicNumber));
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }
}