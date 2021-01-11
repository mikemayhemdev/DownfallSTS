package champ.potions;


import basemod.abstracts.CustomPotion;
import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;


public class CounterstrikePotion extends CustomPotion {
    public static final String POTION_ID = "champ:CounterPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public CounterstrikePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(potency), potency));
    }

    public CustomPotion makeCopy() {
        return new CounterstrikePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 25;
    }
}


