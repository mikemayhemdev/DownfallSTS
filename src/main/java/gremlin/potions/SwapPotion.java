package gremlin.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import gremlin.actions.RandomGremlinSwapAction;

public class SwapPotion extends CustomPotion {
    public static final String POTION_ID = "gremlin:SwapPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SwapPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.S, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        description = DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToTop(new RandomGremlinSwapAction());
    }

    public CustomPotion makeCopy() {
        return new SwapPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}

