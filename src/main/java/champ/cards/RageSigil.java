package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RageSigil extends AbstractChampCard {

    public final static String ID = makeID("RageSigil");

    //stupid intellij stuff skill, self, common

    public RageSigil() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();
        berserkOpen();
        if (bcombo()) {
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