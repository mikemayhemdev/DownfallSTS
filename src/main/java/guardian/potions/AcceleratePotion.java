package guardian.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.GuardianMod;
import guardian.actions.AccelerateAllCardsInStasisAction;
import guardian.characters.GuardianCharacter;
import guardian.orbs.StasisOrb;

public class AcceleratePotion extends CustomPotion {
    public static final String POTION_ID = "Guardian:AcceleratePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public AcceleratePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.M, PotionColor.STEROID);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = GuardianCharacter.cardRenderColor;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        if (Settings.language == Settings.GameLanguage.ENG) {
            this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper("guardianmod:accelerate")), GameDictionary.keywords.get("guardianmod:accelerate")));
            this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordProper("guardianmod:stasis")), GameDictionary.keywords.get("guardianmod:stasis")));
        }
    }

    public void use(AbstractCreature target) {
        for (int i = 0; i < this.potency; i++)
            addToBot(new AccelerateAllCardsInStasisAction());
    }


    public CustomPotion makeCopy() {
        return new AcceleratePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}


