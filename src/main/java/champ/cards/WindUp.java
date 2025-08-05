package champ.cards;

import champ.ChampMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;

import static champ.ChampMod.loadJokeCardImage;

import java.util.ArrayList;

public class WindUp extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("WindUp");

    //stupid intellij stuff skill, enemy, uncommon

    public WindUp() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        tags.add(ChampMod.OPENER);
        postInit();
        loadJokeCardImage(this, "WindUp.png");
    }



    public void use(AbstractPlayer p, AbstractMonster m) {

        this.tags.remove(ChampMod.OPENERBERSERKER);
        this.tags.remove(ChampMod.OPENERDEFENSIVE);
        //if (upgraded) techique();
        atb(new OctoChoiceAction(m, this));
        postInit();

    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoBerserk", this.name, ChampMod.makeCardPath("OctoStanceBerserker.png"), this.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, ChampMod.makeCardPath("OctoStanceDefensive.png"), this.EXTENDED_DESCRIPTION[1]));
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
        }

        atb(new FetchAction(AbstractDungeon.player.drawPile, c -> (c.hasTag(ChampMod.FINISHER))));
    }



    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}