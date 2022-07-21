package guardian.powers;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.GuardianMod;


public class BeamBuffPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:BeamBuffPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;

    public BeamBuffPower(int amount) {
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.setImage("BeamBuff84.png", "BeamBuff32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        updateDescription();
    }

    public BeamBuffPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this(amount);
        this.owner = owner;
        this.source = source;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return super.atDamageGive(card.hasTag(GuardianMod.BEAM) ? damage + this.amount : damage, type, card);
    }
}