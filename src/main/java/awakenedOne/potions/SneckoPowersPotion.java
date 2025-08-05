package awakenedOne.potions;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.SerpentIdolPowerAction;
import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import expansioncontent.potions.BossPotion;
import sneckomod.SneckoMod;

import static awakenedOne.util.Wiz.atb;

public class SneckoPowersPotion extends CustomPotion {
    public static final String POTION_ID = "awakened:SneckoPowersPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public SneckoPowersPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SNECKO, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = AwakenedOneMod.potionLabColor;
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
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle(SneckoMod.makeID("offclass"))), BaseMod.getKeywordDescription(SneckoMod.makeID("offclass"))));
    }

    public void use(AbstractCreature target) {
        atb(new SerpentIdolPowerAction(this.potency));
    }

    public CustomPotion makeCopy() {
        return new BossPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
