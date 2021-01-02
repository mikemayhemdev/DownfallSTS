package champ.cards;

import champ.ChampMod;
import champ.powers.BerserkerStylePower;
import champ.powers.FocusedBerPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class FocusedBerserking extends AbstractChampCard {

    public final static String ID = makeID("FocusedBerserking");

    //stupid intellij stuff skill, self, common

    public FocusedBerserking() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(ChampMod.OPENERBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        berserkOpen();
        applyToSelf(new FocusedBerPower(10));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}