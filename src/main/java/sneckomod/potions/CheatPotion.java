package sneckomod.potions;


import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import sneckomod.SneckoMod;
import sneckomod.powers.CheatPower;


public class CheatPotion extends CustomPotion {
    public static final String POTION_ID = "sneckomod:CheatPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public CheatPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.H, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = SneckoMod.placeholderColor;
    }


    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            this.description = potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CheatPower(this.potency), this.potency));

    }


    public CustomPotion makeCopy() {
        return new CheatPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


