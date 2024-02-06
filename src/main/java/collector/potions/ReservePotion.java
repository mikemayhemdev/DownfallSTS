package collector.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import collector.CollectorMod;
import collector.actions.GainReservesAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;


public class ReservePotion extends CustomPotion {
    public static final String POTION_ID = makeID(ReservePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ReservePotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.H, PotionColor.ELIXIR); //TODO: Potion visual stuff
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor= CollectorMod.potionLabColor;
    }

    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            this.description = potionStrings.DESCRIPTIONS[1] + potency + potionStrings.DESCRIPTIONS[2];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle("collector:reserve")), BaseMod.getKeywordDescription("collector:reserve")));
    }

    public void use(AbstractCreature target) {
        atb(new GainReservesAction(potency));
    }

    public CustomPotion makeCopy() {
        return new ReservePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}
