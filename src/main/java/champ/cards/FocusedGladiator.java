package champ.cards;

import champ.ChampMod;
import champ.powers.FocusedGladPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class FocusedGladiator extends AbstractChampCard {

    public final static String ID = makeID("FocusedGladiator");

    //stupid intellij stuff skill, self, uncommon

    public FocusedGladiator() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        gladOpen();
        applyToSelf(new FocusedGladPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}