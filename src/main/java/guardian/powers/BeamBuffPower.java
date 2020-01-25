package guardian.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;


public class BeamBuffPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:BeamBuffPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public BeamBuffPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.setImage("BeamBuff84.png", "BeamBuff32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];

    }

}