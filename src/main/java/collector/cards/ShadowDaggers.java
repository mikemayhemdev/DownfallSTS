package collector.cards;

import collector.cards.AbstractCollectorCard;
import collector.cards.collectibles.AbstractCollectibleCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class ShadowDaggers extends AbstractCollectorCard {
    public final static String ID = makeID(ShadowDaggers.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 4, 2, , , , 

    public ShadowDaggers() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> q instanceof AbstractCollectibleCard).count(); i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
    }

    //TODO: Card display thing

    public void upp() {
        upgradeDamage(2);
    }
}