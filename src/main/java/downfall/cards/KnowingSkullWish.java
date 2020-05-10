package downfall.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import sneckomod.actions.ChangeGoldAction;

import java.util.ArrayList;

public class KnowingSkullWish extends AbstractExpansionCard {

    public final static String ID = makeID("KnowingSkullWish");

    //stupid intellij stuff SKILL, SELF, RARE

    public KnowingSkullWish() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        isEthereal = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("ks:0", "Wish for Souls", expansionContentMod.makeCardPath("QuickGuardian.png"), "Lose 6 HP. NL Gain 40 Souls."));
        cardList.add(new OctoChoiceCard("ks:1", "Wish for Success", expansionContentMod.makeCardPath("QuickGuardian.png"), "Lose 3 HP. NL Get a Colorless card."));
        cardList.add(new OctoChoiceCard("ks:2", "Wish for A Pick Me Up", expansionContentMod.makeCardPath("QuickGuardian.png"), "Lose 9 HP. NL Get a Potion."));
        return cardList;
    }

    public void doChoiceStuff(OctoChoiceCard card) {
        switch (card.cardID) {
            case "ks:0": {
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 6));
                atb(new ChangeGoldAction(40));
                break;
            }
            case "ks:1": {
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 3));
                AbstractCard q = AbstractDungeon.returnColorlessCard();
                atb(new AddCardToDeckAction(q));
                break;
            }
            case "ks:2": {
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 9));
                atb(new ObtainPotionAction(PotionHelper.getRandomPotion()));
                break;
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
}