package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class BearCard extends AbstractCollectibleCard {
    public final static String ID = makeID(BearCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 3, 10, 3, , 

    public BearCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        baseBlock = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new RemoveAllBlockAction(m, p));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}