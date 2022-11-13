package champ.cards;

import champ.powers.GladiatorStylePower;
import champ.powers.IronFortressPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WarmupRoutine extends AbstractChampCard {

    public final static String ID = makeID("WarmupRoutine");

    //stupid intellij stuff power, self, uncommon

    public WarmupRoutine() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        //tags.add(ChampMod.TECHNIQUE);
        //techniqueLast = true;
        //postInit();
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GladiatorStylePower(magicNumber));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}