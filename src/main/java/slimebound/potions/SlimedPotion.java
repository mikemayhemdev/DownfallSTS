package slimebound.potions;


import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import slimebound.SlimeboundMod;
import slimebound.powers.SlimedPower;


public class SlimedPotion extends CustomPotion {
    public static final String POTION_ID = "Slimebound:SlimedPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public SlimedPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOTTLE, PotionColor.WEAK);
        this.isThrown = true;
        this.targetRequired = true;
        this.labOutlineColor = SlimeboundMod.SLIME_COLOR;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(target, AbstractDungeon.player, new SlimedPower(target, AbstractDungeon.player, this.potency + SlimeboundMod.getAcidTongueBonus(AbstractDungeon.player)), this.potency + SlimeboundMod.getAcidTongueBonus(AbstractDungeon.player)));
    }


    public CustomPotion makeCopy() {
        return new SlimedPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 15;
    }
}


