package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldSigil extends AbstractChampCard {

    public final static String ID = makeID("ShieldSigil");


    public ShieldSigil() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.TECHNIQUE);
        baseMagicNumber = magicNumber = 3;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            techique();
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}