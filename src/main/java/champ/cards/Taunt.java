package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.EtherealMod;
import downfall.util.OctopusCard;
import sneckomod.util.ExhaustMod;

import java.util.ArrayList;

public class Taunt extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("Taunt");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    public Taunt() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        if (upgraded) {
            for (AbstractMonster q : monsterList()) {
                applyToEnemy(q, autoWeak(q, 1));
            }
        } else {
            applyToEnemy(m, autoWeak(m, 1));
        }

        atb(new OctoChoiceAction(this));
    }


    public ArrayList<OctoChoiceCard> choiceList() {
        StanceDance c = new StanceDance();
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        cardList.add(new OctoChoiceCard("octo:OctoBerserk", this.name, ChampMod.makeCardPath("OctoStanceBerserker.png"), c.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, ChampMod.makeCardPath("OctoStanceDefensive.png"), c.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoGladiat", this.name, ChampMod.makeCardPath("OctoStanceGladiator.png"), c.EXTENDED_DESCRIPTION[2]));
        return cardList;
    }

    public void doChoiceStuff(OctoChoiceCard card) {
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
        target = CardTarget.ALL_ENEMY;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}