package awakenedOne.cards;

import awakenedOne.powers.InvasionPower;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Invasion extends AbstractAwakenedCard {
    public final static String ID = makeID(Invasion.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Invasion() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(Invasion.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new InvasionPower(1));
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeBaseCost(2);
    }
}