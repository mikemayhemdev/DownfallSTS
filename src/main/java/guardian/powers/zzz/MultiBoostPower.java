package guardian.powers.zzz;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;
import guardian.powers.AbstractGuardianPower;


public class MultiBoostPower extends AbstractGuardianPower {
    public static final String POWER_ID = "Guardian:MultiBoostPower";
    public static PowerType POWER_TYPE = PowerType.BUFF;

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;


    public MultiBoostPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.setImage("Orbwalk84.png", "Orbwalk32.png");
        this.type = POWER_TYPE;
        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        updateMultihitEffects();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateMultihitEffects();
    }

    public void updateDescription() {
        if (this.amount == 1){
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void updateMultihitEffects() {

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractGuardianCard) {
                if (c.hasTag(GuardianMod.MULTIHIT)) {
                    ((AbstractGuardianCard) c).upgradeMultihit();
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof AbstractGuardianCard) {
                if (c.hasTag(GuardianMod.MULTIHIT)) {
                    ((AbstractGuardianCard) c).upgradeMultihit();
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractGuardianCard) {
                if (c.hasTag(GuardianMod.MULTIHIT)) {
                    ((AbstractGuardianCard) c).upgradeMultihit();
                }
            }
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractGuardianCard) {
                if (c.hasTag(GuardianMod.MULTIHIT)) {
                    ((AbstractGuardianCard) c).upgradeMultihit();
                }
            }
        }


    }
}