package champ.cards;

import champ.ChampMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;

import java.util.ArrayList;

public class WindUp extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("WindUp");

    //stupid intellij stuff skill, enemy, uncommon

    public WindUp() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        tags.add(ChampMod.OPENER);
       
    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        this.tags.remove(ChampMod.OPENERBERSERKER);
        this.tags.remove(ChampMod.OPENERDEFENSIVE);
        this.tags.remove(ChampMod.OPENERGLADIATOR);
        //if (upgraded) techique();
        atb(new OctoChoiceAction(m, this));
       

    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoBerserk", this.name, ChampMod.makeCardPath("OctoStanceBerserker.png"), this.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, ChampMod.makeCardPath("OctoStanceDefensive.png"), this.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoGladiator", this.name, ChampMod.makeCardPath("OctoStanceGladiator.png"), this.EXTENDED_DESCRIPTION[2]));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoBerserk":
                ChampMod.berserkOpen();
                break;
            case "octo:OctoDefense":
                ChampMod.defenseOpen();
                break;
            case "octo:OctoGladiator":
                ChampMod.gladiatorOpen();
                break;
        }

        atb(new FetchAction(AbstractDungeon.player.drawPile, c -> (c.hasTag(ChampMod.COMBO))));
    }


    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}