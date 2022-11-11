package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AllOut extends AbstractChampCard {

    public final static String ID = makeID("AllOut");

    //stupid intellij stuff skill, self, common

    public AllOut() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        //TODO - Create a copy of current Stance's finisher.
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}