package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import theHexaghost.HexaMod;

import java.util.ArrayList;

public class Control extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("Control");

    //stupid intellij stuff skill, self, common

    public Control() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, ChampMod.makeCardPath("OctoStanceDefensive.png"), upgraded ? this.EXTENDED_DESCRIPTION[2] : this.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoBerserk", this.name, ChampMod.makeCardPath("OctoStanceBerserker.png"), upgraded ? this.EXTENDED_DESCRIPTION[3] : this.EXTENDED_DESCRIPTION[1]));
        return cardList;
    }

    public void doChoiceStuff(OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoDefense":
                defenseOpen();
                ArrayList<AbstractCard> qCardList = new ArrayList<AbstractCard>();
                for (AbstractCard t : CardLibrary.getAllCards()) {
                    if (t.rawDescription.contains("Defensive champ:Combo")) qCardList.add(t);
                }
                AbstractCard q = qCardList.get(AbstractDungeon.cardRandomRng.random(qCardList.size() - 1));
                if (upgraded) q.upgrade();
                makeInHand(q);
                break;
            case "octo:OctoBerserk":
                berserkOpen();
                ArrayList<AbstractCard> rCardList = new ArrayList<AbstractCard>();
                for (AbstractCard t : CardLibrary.getAllCards()) {
                    if (t.rawDescription.contains("Berserker champ:Combo")) rCardList.add(t);
                }
                AbstractCard x = rCardList.get(AbstractDungeon.cardRandomRng.random(rCardList.size() - 1));
                if (upgraded) x.upgrade();
                makeInHand(x);
                break;
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}