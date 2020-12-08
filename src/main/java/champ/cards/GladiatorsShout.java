package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GladiatorsShout extends AbstractChampCard {

    public final static String ID = makeID("GladiatorsShout");

    //stupid intellij stuff skill, self, uncommon

    public GladiatorsShout() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        gladOpen();
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}