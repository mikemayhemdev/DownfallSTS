package awakenedOne.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.HexCurse;

public class DogmaPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = DogmaPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public DogmaPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }


    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();

            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
                        HexCurse(this.amount, monster, AbstractDungeon.player);
                    }
                }
            }

        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}