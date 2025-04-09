package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class RageSigil extends AbstractChampCard {

    public final static String ID = makeID("RageSigil");

    public RageSigil() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 2;
        baseMagicNumber = magicNumber = 3;
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        postInit();
        loadJokeCardImage(this, "RageSigil.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        blck();
        atb(new ScryAction(magicNumber));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}