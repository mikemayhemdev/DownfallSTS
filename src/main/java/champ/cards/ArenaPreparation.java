package champ.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.cardmods.RetainCardMod;

import java.util.ArrayList;

public class ArenaPreparation extends AbstractChampCard {

    public final static String ID = makeID("ArenaPreparation");

    //stupid intellij stuff skill, self, uncommon

    public ArenaPreparation() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        exhaust = true;
        // tags.add(ChampMod.TECHNIQUE);
        tags.add(CardTags.HEALING);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //techique();
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL);
            c.isSeen = true;
            UnlockTracker.markCardAsSeen(c.cardID);
            if (!c.selfRetain) {
                CardModifierManager.addModifier(c, new RetainCardMod());
            }
            makeInHand(c);
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}