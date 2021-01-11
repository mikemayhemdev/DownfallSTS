package champ.cards;

import champ.ChampMod;
import champ.powers.FocusedDefPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class FocusedDefense extends AbstractChampCard {

    public final static String ID = makeID("FocusedDefense");

    //stupid intellij stuff skill, self, common

    public FocusedDefense() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        baseBlock = 10;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        defenseOpen();
        applyToSelf(new FocusedDefPower(block));
    }

    public void upp() {
        upgradeBlock(5);
    }
}