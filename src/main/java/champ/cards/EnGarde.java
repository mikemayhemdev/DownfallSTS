package champ.cards;

import champ.powers.EnGardePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class EnGarde extends AbstractChampCard {

    public final static String ID = makeID("EnGarde");

    //stupid intellij stuff skill, self, common

    public EnGarde() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        loadJokeCardImage(this, "EnGarde.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new EnGardePower(block));
    }

    public void upp() {
        upgradeBlock(3);
    }
}