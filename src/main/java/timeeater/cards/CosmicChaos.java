package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class CosmicChaos extends AbstractTimeEaterCard {
    public final static String ID = makeID("CosmicChaos");
    // intellij stuff attack, all_enemy, common, 9, 3, , , , 

    public CosmicChaos() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AttackDamageRandomEnemyAction(this));
        AbstractCard q = this;
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                SuspendHelper.suspend(q);
                AbstractDungeon.player.limbo.removeCard(q);
            }
        });
    }

    public void upp() {
        upgradeDamage(3);
    }
}