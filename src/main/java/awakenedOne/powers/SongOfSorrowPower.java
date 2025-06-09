package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SongOfSorrowPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = SongOfSorrowPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public SongOfSorrowPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

//    @Override
//    public void LoseEnergyAction(int gained) {
//        this.flash();
//        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
//            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
//                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
//                    this.addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new ManaburnPower(monster, amount*gained), amount*gained));
//                }
//            }
//        }
//    }

    @Override
    public void onExhaust(AbstractCard card) {
        this.flash();
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
                    this.addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new ManaburnPower(monster, amount), amount));
                }
            }
        }
    }

    public void updateDescription() {
            description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

}