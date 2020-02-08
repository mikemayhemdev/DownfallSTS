package slimebound.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.actions.TendrilFlailAction;


public class NextTurnBlockAndGoopPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:NextTurnBlockAndGoopPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/PrepareCardS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public NextTurnBlockAndGoopPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.loadRegion("defenseNext");

        this.type = POWER_TYPE;

        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }


    public void updateDescription() {

        if (this.amount == 1) {
            this.description = (
                    DESCRIPTIONS[0] +
                            this.amount +
                            DESCRIPTIONS[1] +
                            (2 + SlimeboundMod.getAcidTongueBonus(this.owner)) +
                            DESCRIPTIONS[2]);
        } else {
            this.description = (
                    DESCRIPTIONS[0] +
                            this.amount +
                            DESCRIPTIONS[1] +
                            (2 + SlimeboundMod.getAcidTongueBonus(this.owner)) +
                            DESCRIPTIONS[3] +
                            this.amount +
                            DESCRIPTIONS[4]);
        }

    }

    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
        AbstractDungeon.actionManager.addToTop(new TendrilFlailAction(this.owner,
                AbstractDungeon.getMonsters().getRandomMonster(true), this.amount, 2 + SlimeboundMod.getAcidTongueBonus(AbstractDungeon.player)));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }


}



