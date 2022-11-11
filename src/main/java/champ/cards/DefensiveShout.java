package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefensiveShout extends AbstractChampCard {

    public final static String ID = makeID("DefensiveShout");

    public DefensiveShout() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
        postInit();
        baseBlock = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        applyToSelf(new CounterPower(1));
        if (upgraded) blck();
    }


    public void upp() {
        upgradeBlock(2);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}