package sneckomod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import java.util.ArrayList;
import sneckomod.SneckoMod;
import sneckomod.util.DiceRollPotionReward;

public class DiceRollPotion extends CustomPotion {
    public static final String POTION_ID = "sneckomod:DiceRollPotion";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("sneckomod:DiceRollPotion");

    public static final String NAME = potionStrings.NAME;

    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DiceRollPotion() {
        super(NAME, "sneckomod:DiceRollPotion", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.FAIRY);
        this.labOutlineColor = SneckoMod.placeholderColor;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        (AbstractDungeon.getCurrRoom()).rewards.add(new DiceRollPotionReward());
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID))
                return true;
        }
        return false;
    }

    public boolean canUse() {
        if (!((AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain))
            return true;
        return false;
    }

    public CustomPotion makeCopy() {
        return new DiceRollPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
