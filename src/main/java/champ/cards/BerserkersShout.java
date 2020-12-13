package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BerserkersShout extends AbstractChampCard {

    public final static String ID = makeID("BerserkersShout");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 3;

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
        fatigue(magicNumber);
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}