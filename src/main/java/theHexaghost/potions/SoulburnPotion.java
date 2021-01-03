package theHexaghost.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import theHexaghost.HexaMod;
import theHexaghost.powers.BurnPower;


public class SoulburnPotion extends CustomPotion {
    public static final String POTION_ID = "hexamod:SoulburnPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public SoulburnPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.FAIRY);
        this.isThrown = true;
        this.targetRequired = true;
        this.labOutlineColor = HexaMod.placeholderColor;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle(HexaMod.makeID("soulburn"))), BaseMod.getKeywordDescription(HexaMod.makeID("soulburn"))));
    }


    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new BurnPower(target, this.potency), this.potency));
    }


    public CustomPotion makeCopy() {
        return new SoulburnPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 30;
    }
}


