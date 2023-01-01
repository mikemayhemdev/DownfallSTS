package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class DualRitual extends AbstractAwakenedCard {
    public final static String ID = makeID(DualRitual.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 23, 5, 23, 5, , 

    public DualRitual() {
        super(ID, 4, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 23;
        baseBlock = 23;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    public void upp() {
        upgradeDamage(5);
        upgradeBlock(5);
    }
}