package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.SpellbinderPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Spellbinder extends AbstractAwakenedCard {
    public final static String ID = makeID(Spellbinder.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public Spellbinder() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(Spellbinder.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SpellbinderPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}