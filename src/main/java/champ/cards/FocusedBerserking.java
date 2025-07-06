package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class FocusedBerserking extends AbstractChampCard {

    public final static String ID = makeID("FocusedBerserking");

    //stupid intellij stuff skill, self, common

    public FocusedBerserking() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.FINISHER);
        postInit();
        loadJokeCardImage(this, "FocusedBerserking.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            finisher(true);
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}