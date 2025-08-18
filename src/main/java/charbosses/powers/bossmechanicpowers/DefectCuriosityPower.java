package charbosses.powers.bossmechanicpowers;

import basemod.interfaces.CloneablePowerInterface;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.cards.AbstractBossCard;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.powers.VenomDebuff;


public class DefectCuriosityPower extends AbstractBossMechanicPower implements OnReceivePowerPower {
    public static final String POWER_ID = "downfall:DefectCuriosity";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DefectCuriosityPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        updateDescription();
        loadRegion("curiosity");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2]);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER && !(card instanceof AbstractBossCard)) {
            flash();
            addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, this.amount), this.amount));
        }
    }

    @Override
    public void atStartOfTurn() {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                ArchetypeAct3OrbsNewAge.resetPretendFocus();
                isDone = true;
            }
        });
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID == StrengthPower.POWER_ID) {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, this.amount*power.amount), this.amount*power.amount));
        }
        if (power.ID == FocusPower.POWER_ID && power.amount < 0 && !owner.hasPower("Artifact") && target == this.owner) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    ArchetypeAct3OrbsNewAge.resetPretendFocus();
                    isDone = true;
                }
            });
        }
        return true;
    }

}

