package theHexaghost.potions;


import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import theHexaghost.HexaMod;
import theHexaghost.actions.DiscoverEtherealAction;


public class EctoCoolerPotion extends CustomPotion {
    public static final String POTION_ID = "hexamod:EctoCoolerPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public EctoCoolerPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.CARD, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = HexaMod.placeholderColor;
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
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.ETHEREAL.NAMES[0]), GameDictionary.keywords.get(GameDictionary.ETHEREAL.NAMES[0])));
    }

    public void use(AbstractCreature target) {

        AbstractDungeon.actionManager.addToBottom(new DiscoverEtherealAction(this.potency));
    }


    public CustomPotion makeCopy() {
        return new EctoCoolerPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


