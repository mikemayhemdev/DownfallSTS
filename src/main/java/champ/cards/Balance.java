package champ.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import theHexaghost.HexaMod;

import java.util.ArrayList;

public class Balance extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("Balance");

    //stupid intellij stuff skill, self, common

    public Balance() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        ;
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoGladiat", this.name, HexaMod.makeCardPath("Float.png"), this.EXTENDED_DESCRIPTION[1]));
        return cardList;
    }

    public void doChoiceStuff(OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoDefense":
                defenseOpen();
                ArrayList<AbstractCard> qCardList = new ArrayList<AbstractCard>();
                qCardList.add(new FlashCut());
                AbstractCard q = qCardList.get(AbstractDungeon.cardRandomRng.random(qCardList.size() - 1));
                if (upgraded) q.upgrade();
                makeInHand(q);
                break;
            case "octo:OctoGladiat":
                gladOpen();
                ArrayList<AbstractCard> rCardList = new ArrayList<AbstractCard>();
                rCardList.add(new SwordSigil());
                AbstractCard r = rCardList.get(AbstractDungeon.cardRandomRng.random(rCardList.size() - 1));
                if (upgraded) r.upgrade();
                makeInHand(r);
                break;
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}