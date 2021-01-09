package champ.potions;


import basemod.abstracts.CustomPotion;
import champ.ChampMod;
import champ.powers.UltimateFormPower;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;


public class UltimateStancePotion extends CustomPotion {
    public static final String POTION_ID = "champ:UltimateStancePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public UltimateStancePotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.H, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = ChampMod.potionLabColor;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        addToBot(new ChangeStanceAction(UltimateStance.STANCE_ID));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new UltimateFormPower(potency), potency));
    }

    public CustomPotion makeCopy() {
        return new UltimateStancePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


