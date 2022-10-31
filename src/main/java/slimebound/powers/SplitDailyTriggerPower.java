package slimebound.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_M;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_S;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_M;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_S;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import slimebound.monsters.AcidSlimeLDailyMod;
import slimebound.monsters.SlimeBossDailyMod;
import slimebound.monsters.SpikeSlimeLDailyMod;

public class SplitDailyTriggerPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:SplitDailyTriggerPower";
    public static final String NAME = "Slime Sacrifice";
    public static final String IMG = "powers/SplitForLessS.png";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private boolean isActive;

    public SplitDailyTriggerPower(AbstractMonster owner) {
        this(owner, 1);
    }

    public SplitDailyTriggerPower(AbstractMonster owner, int amount) {

        this.ID = POWER_ID;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.updateDescription();
        this.img = new com.badlogic.gdx.graphics.Texture(slimebound.SlimeboundMod.getResourcePath(IMG));

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];

    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            AbstractMonster m = (AbstractMonster) this.owner;
            ////SlimeboundMod.logger.info(m.name + " lost HP. Current:" + m.currentHealth + ", Max: " + m.maxHealth + ", damage: " + damageAmount);
            if ((float) m.currentHealth - (float) damageAmount <= (float) m.maxHealth / 2) {
                this.isActive = true;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, ArtifactPower.POWER_ID));

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StunMonsterPower(m, 1), 1));
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(m, TextAboveCreatureAction.TextType.INTERRUPTED));
            }

        }

        return super.onAttacked(info, damageAmount);
    }


    public void atStartOfTurn() {
        if (isActive) {
            AbstractMonster m = (AbstractMonster) this.owner;
            float saveX = m.hb.cX;
            float saveY = AbstractDungeon.floorY;
            AbstractDungeon.actionManager.addToBottom(new CannotLoseAction());
            AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(m, 1.0F, 0.1F));
            AbstractDungeon.actionManager.addToBottom(new HideHealthBarAction(m));
            AbstractDungeon.actionManager.addToBottom(new SuicideAction(m, false));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("SLIME_SPLIT"));

            //AbstractDungeon.getMonsters().monsters.remove(m);

            if (m.currentHealth < 20) {
                AbstractMonster mini1 = new AcidSlime_S(saveX - 55F, -4F, 0);
                mini1.maxHealth = m.currentHealth;
                mini1.currentHealth = m.currentHealth;
                mini1.drawX = m.drawX + 55F;

                mini1.usePreBattleAction();
                mini1.useUniversalPreBattleAction();
                AbstractMonster mini2 = new SpikeSlime_S(saveX + 55F, 4F, 0);
                mini2.maxHealth = m.currentHealth;
                mini2.currentHealth = m.currentHealth;
                mini2.drawX = m.drawX - 55F;
                mini2.usePreBattleAction();
                mini2.useUniversalPreBattleAction();

                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini1, false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini2, false));

            } else if (m.currentHealth < 40) {
                AbstractMonster mini1 = new AcidSlime_M(saveX - 70F, -4F, 0, m.currentHealth);

                mini1.drawX = m.drawX + 70F;

                mini1.usePreBattleAction();
                mini1.useUniversalPreBattleAction();
                AbstractMonster mini2 = new SpikeSlime_M(saveX + 70F, 4F, 0, m.currentHealth);

                mini2.drawX = m.drawX - 70F;
                mini2.usePreBattleAction();
                mini2.useUniversalPreBattleAction();

                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini1, false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini2, false));
            } else if (m.currentHealth < 80) {
                AbstractMonster mini1 = new AcidSlimeLDailyMod(saveX - 100F, -4F, 0, m.currentHealth);

                mini1.drawX = m.drawX + 100F;

                mini1.usePreBattleAction();
                mini1.useUniversalPreBattleAction();
                AbstractMonster mini2 = new SpikeSlimeLDailyMod(saveX + 100F, 4F, 0, m.currentHealth);

                mini2.drawX = m.drawX - 100F;
                mini2.usePreBattleAction();
                mini2.useUniversalPreBattleAction();

                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini1, false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(mini2, false));
            } else {
                AbstractMonster boss1 = new SlimeBossDailyMod();
                // boss1.hb_x = saveX + 134.0F;
                boss1.drawX = m.drawX + 164.0F;
                boss1.maxHealth = m.currentHealth;
                boss1.currentHealth = m.currentHealth;

                boss1.usePreBattleAction();
                boss1.useUniversalPreBattleAction();
                AbstractMonster boss2 = new SlimeBossDailyMod();
                //boss2.hb_x = saveX - 134.0F;
                boss2.drawX = m.drawX - 164.0F;
                boss2.maxHealth = m.currentHealth;
                boss2.currentHealth = m.currentHealth;

                boss2.usePreBattleAction();
                boss2.useUniversalPreBattleAction();
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(boss1, false));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(boss2, false));
            }
            AbstractDungeon.actionManager.addToBottom(new CanLoseAction());

        }


    }
}
