package theHexaghost.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.ChargeAction;
import theHexaghost.actions.ExtinguishAction;


public class DoubleChargePotion extends CustomPotion {
    public static final String POTION_ID = "hexamod:DoubleChargePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public DoubleChargePotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.FAIRY);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = HexaMod.placeholderColor;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle(HexaMod.makeID("ignite"))), BaseMod.getKeywordDescription(HexaMod.makeID("ignite"))));
    }

    public void use(AbstractCreature target) {
        for (int i = 0; i < this.potency; i++) {
            AbstractDungeon.actionManager.addToBottom(new ExtinguishAction(GhostflameHelper.activeGhostFlame));
            AbstractDungeon.actionManager.addToBottom(new ChargeAction(GhostflameHelper.activeGhostFlame));
        }
    }


    public CustomPotion makeCopy() {
        return new DoubleChargePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }
}


