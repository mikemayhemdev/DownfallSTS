package charbosses.powers.bossmechanicpowers;

import charbosses.actions.unique.EnemyPoisonDamageAction;
import charbosses.powers.general.EnemyPoisonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.downfallMod;

public class SilentPoisonPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:SilentPoisonPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public SilentPoisonPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void atStartOfTurn() {
        if(AbstractDungeon.player.hasPower(EnemyPoisonPower.POWER_ID)){
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractPower poison = AbstractDungeon.player.getPower(EnemyPoisonPower.POWER_ID);
                poison.flashWithoutSound();
                this.addToBot(new EnemyPoisonDamageAction(poison.owner, this.owner, poison.amount, AbstractGameAction.AttackEffect.POISON));
            }
        }
    }


    public void updateDescription() {
        if (downfallMod.useLegacyBosses) {

            this.description = DESC[0];
        }
            else {

                this.description = DESC[1];
            }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
