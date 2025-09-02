package charbosses.powers.general;

import charbosses.actions.unique.EnemyPoisonDamageAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Silent.Simpler.ArchetypeAct1PoisonSimple;
import charbosses.relics.CBR_SneckoSkull;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import downfall.downfallMod;
import slimebound.SlimeboundMod;

public class EnemyPoisonPower extends AbstractPower {
    public static String POWER_ID = "Poison";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;

    public EnemyPoisonPower(AbstractCreature owner, AbstractCreature source, int poisonAmt) {
        this.name = NAME;
        this.ID = "Poison";
        this.owner = owner;
        this.source = source;
        this.amount = poisonAmt;
        if( AbstractCharBoss.boss.hasRelic(CBR_SneckoSkull.ID) ){
            this.amount++;
        }
        if (this.amount >= 9999) {
            this.amount = 9999;
        }

        this.updateDescription();
        this.loadRegion("poison");
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;  // without the positive check, your antidote will reduce 1 less, because it will apply a negative amount, which would get + 1
                                                        // by snecko skull
        if( (stackAmount >= 0) && AbstractCharBoss.boss.hasRelic(CBR_SneckoSkull.ID) ){
            this.amount ++;
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (AbstractCharBoss.boss != null){
            if (AbstractCharBoss.boss.chosenArchetype != null){
                if (AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct1PoisonSimple){
                    SlimeboundMod.logger.info("Resetting poison boss");
                    ((CharBossSilent) AbstractCharBoss.boss).resetPoisonBoss();
                }
            }
        }
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void updateDescription() {
        if (downfallMod.useLegacyBosses) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {

            this.description = CardCrawlGame.languagePack.getPowerStrings("downfall:OnPlayerPoison").DESCRIPTIONS[0] + this.amount + CardCrawlGame.languagePack.getPowerStrings("downfall:OnPlayerPoison").DESCRIPTIONS[1];
        }
    }

//     Damage action moved to SilentPoisonPower so that it happens after Afterlife activation
//    @Override
//    public void atEndOfTurn(boolean isPlayer) {
//        if (isPlayer) {
//            if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
//                this.flashWithoutSound();
//                this.addToBot(new EnemyPoisonDamageAction(this.owner, this.source, this.amount, AttackEffect.POISON));
//                //Poison reduction/removal handled in damage action
//            }
//        }
//    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Poison");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
