package slimebound.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.SlimeboundMod;


public class FirmFortitudePower extends TwoAmountPower {
    public static final String POWER_ID = "Slimebound:FirmFortitudePower";
    public static final String NAME = "Slime Sacrifice";
    public static final String IMG = "powers/FirmFortitude.png";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private boolean isActive = true;
    private AbstractCreature source;

    public FirmFortitudePower(AbstractCreature owner, int bufferAmt) {
        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = bufferAmt;

        this.owner = owner;


        this.img = new com.badlogic.gdx.graphics.Texture(slimebound.SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        this.amount2 = this.amount;
        updateDescription();

    }


    public void atEndOfTurn(boolean isPlayer) {
        this.isActive = false;
        this.amount2 = this.amount;
        updateDescription();
    }


    public void atStartOfTurn() {

        this.isActive = true;
        this.amount2 = this.amount;
        updateDescription();
    }


    public int onLoseHp(int damageAmount) {

        if (damageAmount > 0 && this.isActive && this.amount2 > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new DamageRandomEnemyAction(new DamageInfo(this.owner, damageAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

            this.amount2--;
            updateDescription();
            return 0;
        }

        return damageAmount;
    }

    public int onAttackedPreBlock(DamageInfo info, int damageAmount) {
        //SlimeboundMod.logger.info("onAttacked triggered " + damageAmount + " " + this.isActive + " " + this.amount2);
        if (damageAmount > 0 && this.isActive && this.amount2 > 0) {
            //SlimeboundMod.logger.info("Firmfortitude triggered " + damageAmount);
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(this.owner, damageAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

            this.amount2--;
            updateDescription();
            return 0;
        } else {
            return damageAmount;

        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.amount2 += stackAmount;
        updateDescription();
    }


    public void updateDescription() {
        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }

        if (this.amount2 == 0) {
            this.description += DESCRIPTIONS[6];
        } else if (this.amount == 1) {
            this.description += DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[5];
        } else {
            this.description += DESCRIPTIONS[3] + this.amount2 + DESCRIPTIONS[4];
        }
    }
}


