package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class SnakePlantCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SnakePlantCard.class.getSimpleName());
    // intellij stuff attack, enemy, common, 7, 2, , , , 

    public SnakePlantCard() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Use snake plant's VFX. Actually most cards should be using associated monster vfx when possible
        for (int i = 0; i < 3; i++) {
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        }
    }

    public void upp() {
        upgradeDamage(2);
    }
}