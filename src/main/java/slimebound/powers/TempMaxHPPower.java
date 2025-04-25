package slimebound.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.SlimeboundMod;
import slimebound.actions.IncreaseMaxHpFlatAction;


public class TempMaxHPPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:MaxHPPower";
    public static final String NAME = "Grown";
    public static final String IMG = "powers/FirmFortitude.png";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private boolean isActive = true;
    private AbstractCreature source;

    public TempMaxHPPower(AbstractCreature owner, int bufferAmt) {
        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = bufferAmt;

        this.owner = owner;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void onVictory() {
        addToBot(new IncreaseMaxHpFlatAction(owner, amount *-1, false));

    }



    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        addToBot(new IncreaseMaxHpFlatAction(owner, stackAmount, true));
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}


