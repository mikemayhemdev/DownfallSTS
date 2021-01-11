package champ.cards;

import champ.ChampMod;
import champ.powers.BerserkerStylePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BerserkerStyle extends AbstractChampCard {

    public final static String ID = makeID("BerserkerStyle");

    //stupid intellij stuff power, self, uncommon

    public BerserkerStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERBERSERKER);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        berserkOpen();
        applyToSelf(new BerserkerStylePower(magicNumber));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}