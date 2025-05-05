package awakenedOne.cards;

import awakenedOne.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.att;

public class FeatherWhirl extends AbstractAwakenedCard {
    public final static String ID = makeID(FeatherWhirl.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public FeatherWhirl() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 2;
        this.exhaust = true;
    }

 public void use(AbstractPlayer p, AbstractMonster m) {
        att(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                AbstractMonster q = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
               this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
               this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
            }
            return true;
        }));
    }


    public void upp() {
        upgradeDamage(2);
    }
}