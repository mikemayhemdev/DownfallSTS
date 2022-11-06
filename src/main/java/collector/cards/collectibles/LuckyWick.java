package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import collector.cards.AbstractCollectorCard;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class LuckyWick extends AbstractCollectibleCard {
    public final static String ID = makeID("LuckyWick");
    // intellij stuff attack, enemy, special, 3, 2, 3, 2, , 

    public LuckyWick() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 3;
        baseBlock = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}