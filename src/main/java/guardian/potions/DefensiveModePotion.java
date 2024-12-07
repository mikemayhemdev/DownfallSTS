package guardian.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import guardian.powers.*;
import guardian.relics.ModeShifterPlus;
import guardian.stances.DefensiveMode;
import guardian.characters.GuardianCharacter;

public class DefensiveModePotion extends CustomPotion {
    public static final String POTION_ID = "Guardian:DefensiveModePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public DefensiveModePotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = GuardianCharacter.cardRenderColor;
    }


    public void initializeData() {
        this.potency = getPotency(0);
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper("guardianmod:defensive mode")), GameDictionary.keywords.get("guardianmod:defensive mode")));

    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, potency));
        AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(DefensiveMode.STANCE_ID));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DontLeaveDefensiveModePower(AbstractDungeon.player, 1), 1));


        ModeShifterPlus modeShifterPlusInstance = new ModeShifterPlus();
        if (AbstractDungeon.player.hasRelic(ModeShifterPlus.ID)) {
            if (!AbstractDungeon.actionManager.turnHasEnded) {
                addToTop(new GainEnergyAction(1));
            }
            if (AbstractDungeon.actionManager.turnHasEnded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new EnergizedGuardianPower(target, 1)));
            }
            addToTop(new DrawCardAction(AbstractDungeon.player, 2));
            modeShifterPlusInstance.flash();
        }

        if (AbstractDungeon.player.hasPower(RevengePower.POWER_ID)) {
            RevengePower revengePower =
                    (RevengePower) AbstractDungeon.player.getPower(RevengePower.POWER_ID);

            if (revengePower != null) {
                revengePower.onActivateCallR(target);
            }
        }

        if (AbstractDungeon.player.hasPower(SpikerProtocolPower.POWER_ID)) {
            SpikerProtocolPower spikerProtocolPower =
                    (SpikerProtocolPower) AbstractDungeon.player.getPower(SpikerProtocolPower.POWER_ID);

            if (spikerProtocolPower != null) {
                spikerProtocolPower.onActivateCallS(target);
            }
        }


        if (AbstractDungeon.player.hasPower(EvasiveProtocolPower.POWER_ID)) {
            EvasiveProtocolPower evasiveProtocolPower =
                    (EvasiveProtocolPower) AbstractDungeon.player.getPower(EvasiveProtocolPower.POWER_ID);

            if (evasiveProtocolPower != null) {
                evasiveProtocolPower.onActivateCallE(target);
            }


        }

    }


    public CustomPotion makeCopy() {
        return new DefensiveModePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 16;
    }
}


