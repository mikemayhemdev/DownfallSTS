package awakenedOne.cards;

import awakenedOne.powers.DarkEchoPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class DarkEcho extends AbstractAwakenedCard {
    public final static String ID = makeID(DarkEcho.class.getSimpleName());
    // intellij stuff attack, all_enemy, rare, 18, 4, , , 2, 1

    public DarkEcho() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseDamage = 4;
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DarkEchoPower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}