package slimebound.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class SlimeRitualPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:SlimeRitualPower";
    public static final String NAME = "Slime Sacrifice";
    public static final String IMG = "powers/ritual.png";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;

    public SlimeRitualPower(AbstractCreature owner, int strAmt) {

        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = strAmt;

        this.owner = owner;


        this.img = new com.badlogic.gdx.graphics.Texture(slimebound.SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();
    }

    public void updateDescription() {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    public void atStartOfTurn() {

        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));

    }
}


