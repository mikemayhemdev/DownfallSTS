package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OpenFire extends AbstractBronzeCard {

    public final static String ID = makeID("OpenFire");

    //stupid intellij stuff skill, self, basic

    public OpenFire() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        fireBlaster(1);
    }

    public void upp() {
        //TODO: Is choosing between Blaster or Shields too strong? Think on this
    }
}