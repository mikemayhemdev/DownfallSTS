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
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERBERSERKER);
        baseDamage = 10;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        berserkOpen();
        applyToSelf(new FocusedBerPower(damage));
    }

    public void upp() {
        upgradeDamage(5);
    }
}