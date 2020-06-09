package sneckomod.potions;


import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import sneckomod.SneckoMod;


public class OffclassReductionPotion extends CustomPotion {
    public static final String POTION_ID = "sneckomod:OffclassReductionPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public OffclassReductionPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SNECKO, PotionColor.SMOKE);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = SneckoMod.placeholderColor;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(TipHelper.capitalize(BaseMod.getKeywordTitle(SneckoMod.makeID("offclass"))), BaseMod.getKeywordDescription(SneckoMod.makeID("offclass"))));

    }

    public void use(AbstractCreature target) {

        for (int i = 0; i < this.potency; i++) {
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    for (AbstractCard q : AbstractDungeon.player.hand.group) {
                        if (q.color != AbstractDungeon.player.getCardColor()) {
                            q.modifyCostForCombat(-1);
                            q.superFlash();
                        }
                    }
                }
            });
        }
    }


    public CustomPotion makeCopy() {
        return new OffclassReductionPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}


