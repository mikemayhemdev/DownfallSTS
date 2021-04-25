package champ.cards;

import champ.ChampMod;
import champ.powers.IronFortressPower;
import champ.powers.IronFortressVigorPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IronFortress extends AbstractChampCard {

    public final static String ID = makeID("IronFortress");

    //stupid intellij stuff power, self, uncommon

    private static final int MAGIC = 5;

    public IronFortress() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;

        tags.add(ChampMod.TECHNIQUE);
        techniqueLast = true;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (bcombo()){
            applyToSelf(new IronFortressVigorPower(1));
        }
        if (dcombo()){
            applyToSelf(new IronFortressPower(1));
        }
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}