package awakenedOne.cards;

import awakenedOne.actions.SerpentIdolPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
@Deprecated
@CardIgnore
public class ChimeraBlessing extends AbstractAwakenedCard {
    public final static String ID = makeID(ChimeraBlessing.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 4, 2, , , , 

    public ChimeraBlessing() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SerpentIdolPowerAction(1));
    }

    public void upp() {
        this.exhaust = false;
    }
}