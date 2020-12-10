package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BerserkersShout extends AbstractChampCard {

    public final static String ID = makeID("BerserkersShout");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public BerserkersShout() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        berserkOpen();
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}