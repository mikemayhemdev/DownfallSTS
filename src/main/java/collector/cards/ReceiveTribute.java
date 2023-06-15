package collector.cards;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.att;

public class ReceiveTribute extends AbstractCollectorCard {
    public final static String ID = makeID(ReceiveTribute.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 2, 2

    public ReceiveTribute() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        isPyre();
    }

    private static ArrayList<AbstractCard> possibilities;

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (possibilities == null) {
            possibilities = new ArrayList<>();
            for (AbstractCard q : CardLibrary.getAllCards()) {
                if (q.rarity != CardRarity.SPECIAL && q.hasTag(expansionContentMod.STUDY)) {
                    possibilities.add(q.makeCopy());
                }
            }
        }
        ArrayList<AbstractCard> remaining = new ArrayList<>();
        remaining.addAll(possibilities);
        ArrayList<AbstractCard> choices = new ArrayList<>();
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard toAdd = remaining.remove(AbstractDungeon.cardRandomRng.random(remaining.size() - 1)).makeCopy();
            CardModifierManager.addModifier(toAdd, new RetainMod());
            choices.add(toAdd);
        }
        addToBot(new SelectCardsCenteredAction(choices, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            att(new MakeTempCardInHandAction(cards.get(0), true));
        }));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}