package collector.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.forAllMonstersLiving;

public class VoidArmor extends AbstractCollectorCard {
    public final static String ID = makeID(VoidArmor.class.getSimpleName());
    // intellij stuff skill, all, uncommon, , , 13, 3, , 

    public VoidArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        baseBlock = 13;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        forAllMonstersLiving(q -> atb(new GainBlockAction(q, block)));
    }

    public void upp() {
        upgradeBlock(4);
    }
}