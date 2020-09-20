package champ.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefensiveShout extends AbstractChampCard {

    public final static String ID = makeID("DefensiveShout");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public DefensiveShout() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        defenseOpen();
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}