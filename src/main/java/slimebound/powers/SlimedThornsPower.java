package slimebound.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.vfx.SlimeDripsEffectPurple;


public class SlimedThornsPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:SlimedThornsPower";
    public static final String NAME = "UsefulSlime";
    public static final String IMG = "powers/LivingWallS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    public boolean doubleUp = false;
    public boolean triggered = false;
    private AbstractCreature source;


    public SlimedThornsPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = amount;

        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();


    }


    public void updateDescription() {

        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];


    }

    public void onInitialApplication() {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(this.owner.hb.cX, this.owner.hb.cY, 4), 0.05F));

    }


    public void atStartOfTurn() {


        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, SlimedThornsPower.POWER_ID));


    }


    public int onAttacked(DamageInfo info, int damageAmount) {

        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != AbstractDungeon.player) {
            flash();

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, this.owner, new SlimedPower(info.owner, this.owner, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
        }


        return super.onAttacked(info, damageAmount);
    }


}





