package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class BobAndWeave extends AbstractChampCard {

    public final static String ID = makeID("BobAndWeave");

    //stupid intellij stuff skill, self, common

    public BobAndWeave() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);

        baseBlock = 3;
        baseMagicNumber = magicNumber = 2;

        tags.add(ChampMod.OPENER);
        tags.add(ChampMod.OPENERBERSERKER);
       
        loadJokeCardImage(this, "BobAndWeave.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        ChampMod.vigor(magicNumber);
        berserkOpen();
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}