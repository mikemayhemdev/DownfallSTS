package champ.potions;


import basemod.abstracts.CustomPotion;
import champ.ChampMod;
import champ.actions.DiscoverOpenerAction;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;


public class TechPotion extends CustomPotion {
    public static final String POTION_ID = "champ:TechniquePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public TechPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.H, PotionColor.SMOKE);
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
        for (int i = 0; i < potency; i++) {
            if (AbstractDungeon.player.stance instanceof AbstractChampStance)
                ((AbstractChampStance) AbstractDungeon.player.stance).techique();
        }
    }

    public CustomPotion makeCopy() {
        return new TechPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 5;
    }
}


