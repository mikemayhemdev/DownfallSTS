package sneckomod.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleRandomCardAction;


public class MuddlingPotion extends CustomPotion {
    public static final String POTION_ID = "sneckomod:MuddlingPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public MuddlingPotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOLT, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle(SneckoMod.makeID("muddle"))), BaseMod.getKeywordDescription(SneckoMod.makeID("muddle"))));
    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.player.hand.size() > 0) {
            AbstractDungeon.actionManager.addToBottom(new MuddleRandomCardAction(potency, true, true));
        }
    }


    public CustomPotion makeCopy() {
        return new MuddlingPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}


