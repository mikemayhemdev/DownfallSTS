package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;

import java.util.ArrayList;

public class StanceDance extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("StanceDance");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public StanceDance() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();
        atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoBerserk", this.name, ChampMod.makeCardPath("OctoStanceBerserker.png"), this.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, ChampMod.makeCardPath("OctoStanceDefensive.png"), this.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoGladiat", this.name, ChampMod.makeCardPath("OctoStanceGladiator.png"), this.EXTENDED_DESCRIPTION[2]));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoBerserk":
                berserkOpen();
                break;
            case "octo:OctoDefense":
                defenseOpen();
                break;
            case "octo:OctoGladiat":
                gladOpen();
                break;
        }
    }

    public void upp() {
        tags.add(ChampMod.TECHNIQUE);
        initializeDescription();
    }
}