package automaton.potions;


import automaton.powers.CleanCodePower;
import automaton.powers.FreeFunctionPower;
import basemod.abstracts.CustomPotion;
import champ.actions.DiscoverOpenerAction;
import champ.potions.OpenerPotion;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;


public class CleanCodePotion extends CustomPotion {
    public static final String POTION_ID = "bronze:CleanCodePotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public CleanCodePotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.H, PotionColor.ELIXIR);
        this.isThrown = false;
        this.targetRequired = false;
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
    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CleanCodePower(potency), potency));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FreeFunctionPower(potency), potency));
    }

    public CustomPotion makeCopy() {
        return new CleanCodePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
