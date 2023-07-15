package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class VoidArmor extends AbstractCollectorCard {
    public final static String ID = makeID(VoidArmor.class.getSimpleName());
    // intellij stuff skill, all, uncommon, , , 13, 3, , 

    public VoidArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL);
        baseBlock = 10;
        baseMagicNumber = magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        forAllMonstersLiving(q -> atb(new GainBlockAction(q, block)));
        forAllMonstersLiving(q -> {
            if (q.hasPower(DoomPower.POWER_ID)) {
                applyToEnemy(q, new DoomPower(q, magicNumber));
            }
        });
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(2);
    }
}