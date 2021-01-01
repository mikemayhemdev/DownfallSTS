package timeEater.potions;


import automaton.actions.RepeatCardAction;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;

public class StoredCardPotion extends CustomPotion {
    public static final String POTION_ID = "time:StoredCardPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private AbstractCard storedCard;

    public StoredCardPotion(AbstractCard stored) {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOTTLE, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
        this.storedCard = stored;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.name = storedCard.name + potionStrings.DESCRIPTIONS[0];
        this.description = potionStrings.DESCRIPTIONS[1] + storedCard.name + potionStrings.DESCRIPTIONS[2]; // add description handling for higher potency, wide potion fun stuff
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        for (int i = 0; i < potency; i++) {
            AbstractDungeon.actionManager.addToBottom(new RepeatCardAction(storedCard));
        }
    }

    public CustomPotion makeCopy() {
        return new StoredCardPotion(storedCard);
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
