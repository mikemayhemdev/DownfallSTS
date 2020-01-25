package guardian.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;


public class ExhaustStatusesPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:ExhaustStatusesPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;

    public int usedThisTurn = 0;


    public ExhaustStatusesPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.setImage("BronzeOrbWeaken84.png", "BronzeOrbWeaken32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    //ACTUAL EFFECTS IN RECEIVE POST DRAW OF GUARDIANMOD

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.usedThisTurn = 0;
    }

    public void updateDescription() {
        if (this.amount == 1){
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

}