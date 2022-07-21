package expansioncontent.potions;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import downfall.util.TextureLoader;
import expansioncontent.actions.DiscoverBossAction;

public class BossPotion extends CustomPotion {
    public static final String POTION_ID = "expansioncontent:BossPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BossPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.CARD, PotionColor.WEAK);

        ReflectionHacks.setPrivate(this, AbstractPotion.class, "spotsImg", TextureLoader.getTexture("expansioncontentResources/images/potion/potion_boss_spots.png"));

        this.isThrown = false;
        this.targetRequired = false;
    }


    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            this.description = potionStrings.DESCRIPTIONS[1] + this.potency + DESCRIPTIONS[2];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        addToBot(new DiscoverBossAction(this.potency));
    }

    public CustomPotion makeCopy() {
        return new BossPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}