package automaton.potions;


import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


public class BurnAndBuffPotion extends CustomPotion {
    public static final String POTION_ID = "bronze:BurnAndBuffPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BurnAndBuffPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        if (Settings.language == Settings.GameLanguage.ENG) {
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]), GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0])));
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.DEXTERITY.NAMES[0]), GameDictionary.keywords.get(GameDictionary.DEXTERITY.NAMES[0])));
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.BURN.NAMES[0]), GameDictionary.keywords.get(GameDictionary.BURN.NAMES[0])));
        }
    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, potency), potency));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, potency), potency));
        AbstractCard c = new Burn();
        c.upgrade();
        
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c, 2));

    }

    public CustomPotion makeCopy() {
        return new BurnAndBuffPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}
