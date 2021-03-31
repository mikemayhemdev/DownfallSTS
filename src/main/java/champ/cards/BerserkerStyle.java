package champ.cards;

import champ.ChampMod;
import champ.powers.BerserkerStylePower;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class BerserkerStyle extends AbstractChampCard {

    public final static String ID = makeID("BerserkerStyle");

    //stupid intellij stuff power, self, uncommon

    public BerserkerStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERBERSERKER);
        baseMagicNumber = magicNumber = 2;
        myHpLossCost = 5;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        berserkOpen();
        fatigue(5);
        applyToSelf(new BerserkerStylePower(magicNumber));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}