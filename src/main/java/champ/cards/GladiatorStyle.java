package champ.cards;

import champ.ChampMod;
import champ.powers.GladiatorStylePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GladiatorStyle extends AbstractChampCard {

    public final static String ID = makeID("GladiatorStyle");

    //stupid intellij stuff power, self, uncommon

    public GladiatorStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        this.tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERGLADIATOR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        gladOpen();
        applyToSelf(new GladiatorStylePower(3));
    }

    public void upp() {
        isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}