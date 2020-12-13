package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefensiveShout extends AbstractChampCard {

    public final static String ID = makeID("DefensiveShout");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 6;

    public DefensiveShout() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERDEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        defenseOpen();
        applyToSelf(new CounterPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}