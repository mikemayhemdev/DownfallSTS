package automaton.potions;


import automaton.FunctionHelper;
import automaton.cards.AbstractBronzeCard;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;


public class FreeFunctionsPotion extends CustomPotion {
    public static final String POTION_ID = "bronze:FreeFunctionsPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public FreeFunctionsPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOLT, PotionColor.ANCIENT);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        for (AbstractCard q : FunctionHelper.held.group) {
            q.baseDamage += potency;
            q.damage += potency;
            q.baseBlock += potency;
            q.block += potency;
            q.baseMagicNumber += potency;
            q.magicNumber += potency;
            if (q instanceof AbstractBronzeCard) {
                ((AbstractBronzeCard) q).baseAuto += potency;
                ((AbstractBronzeCard) q).auto += potency;
            }
            q.applyPowers();
            q.superFlash();
            FunctionHelper.genPreview();
        }
    }

    public CustomPotion makeCopy() {
        return new FreeFunctionsPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
