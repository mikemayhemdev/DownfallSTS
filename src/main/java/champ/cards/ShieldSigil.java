package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldSigil extends AbstractChampCard {

    public final static String ID = makeID("ShieldSigil");

    //stupid intellij stuff skill, self, common

    public ShieldSigil() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();
        defenseOpen();
        if (dcombo()) {
            techique();
            techique();
            techique();
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}