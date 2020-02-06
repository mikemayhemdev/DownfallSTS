package guardian.potions;


import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import guardian.actions.SwitchToDefenseModeAction;
import guardian.powers.BlockOnCardUsePower;
import slimebound.powers.AcidTonguePowerUpgraded;

public class BlockOnCardUsePotion extends CustomPotion {
    public static final String POTION_ID = "Guardian:BlockOnCardUsePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public BlockOnCardUsePotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.HEART, PotionColor.ELIXIR);
        this.isThrown = false;
        this.targetRequired = false;
    }


    public void initializeData() {
        this.potency = getPotency();

        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

    }

    public void use(AbstractCreature target) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BlockOnCardUsePower(AbstractDungeon.player, this.potency), this.potency));


    }


    public CustomPotion makeCopy() {
        return new BlockOnCardUsePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 4;
    }
}


