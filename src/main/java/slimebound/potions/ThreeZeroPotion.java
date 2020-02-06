package slimebound.potions;


import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;

import java.util.ArrayList;
import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;


public class ThreeZeroPotion extends CustomPotion {
    public static final String POTION_ID = "Slimebound:ThreeZeroPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public ThreeZeroPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.T, PotionColor.POISON);
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
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var1 = srcCommonCardPool.group.iterator();

        AbstractCard c3;


        for (int i = 0; i < this.potency; i++) {
            while (var1.hasNext()) {
                c3 = (AbstractCard) var1.next();
                if (c3.cost == 0) {
                    list.add(c3);
                }
            }

            var1 = srcUncommonCardPool.group.iterator();

            while (var1.hasNext()) {
                c3 = (AbstractCard) var1.next();
                if (c3.cost == 0) {
                    list.add(c3);
                }
            }

            var1 = srcRareCardPool.group.iterator();

            while (var1.hasNext()) {
                c3 = (AbstractCard) var1.next();
                if (c3.cost == 0) {
                    list.add(c3);
                }
            }

            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(list.get(cardRandomRng.random(list.size() - 1))));

        }
    }


    public CustomPotion makeCopy() {
        return new ThreeZeroPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 4;
    }
}


