package sneckomod.potions;


import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import sneckomod.actions.MuddleAction;

import java.util.ArrayList;


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
    }

    public void use(AbstractCreature target) {
        if (AbstractDungeon.player.hand.size() > 0) {
            int x = 0;
            int truecost = 0;
            ArrayList<AbstractCard> possCardsList = new ArrayList<>();
            ArrayList<AbstractCard> chosenCardsList = new ArrayList<>();

            for (AbstractCard c : AbstractDungeon.player.hand.group) possCardsList.add(c);

            AbstractCard highest;

            for (int i = 0; i < this.potency; i++) {
                x = -1;
                highest = null;
                //find highest cost card
                for (AbstractCard q : possCardsList) {
                    if (q.isCostModifiedForTurn) {
                        truecost = q.costForTurn;
                    } else {
                        truecost = q.cost;
                    }
                    if (truecost > x) {
                        x = truecost;
                        highest = q;
                    }
                }

                //remove highest from possible, add to chosen
                if (highest != null) {
                    chosenCardsList.add(highest);
                    possCardsList.remove(highest);
                } else {
                    break;
                }
            }


            if (!chosenCardsList.isEmpty()) {
                for (AbstractCard q : chosenCardsList) {
                    AbstractDungeon.actionManager.addToBottom(new MuddleAction(q));
                }
            }

        }

    }


    public CustomPotion makeCopy() {
        return new MuddlingPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }
}


