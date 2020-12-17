package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
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
import java.util.Collections;

public class Taunt extends AbstractChampCard implements OctopusCard {

    public final static String ID = makeID("Taunt");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    public Taunt() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ALL_ENEMY);
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        for (AbstractMonster q : monsterList()) {
            applyToEnemy(q, autoWeak(q, this.magicNumber));
        }

        if (!upgraded) {
            ArrayList<String> validStances = new ArrayList<>();

            if (!(p.stance instanceof DefensiveStance)) validStances.add(DefensiveStance.STANCE_ID);
            if (!(p.stance instanceof GladiatorStance)) validStances.add(GladiatorStance.STANCE_ID);
            if (!(p.stance instanceof BerserkerStance)) validStances.add(BerserkerStance.STANCE_ID);

            Collections.shuffle(validStances);

            switch (validStances.get(0)) {
                case DefensiveStance.STANCE_ID:
                    defenseOpen();
                    break;
                case GladiatorStance.STANCE_ID:
                    gladOpen();
                    break;
                case BerserkerStance.STANCE_ID:
                    berserkOpen();
                    break;
            }
        } else {
            atb(new OctoChoiceAction(m, this));
        }

    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        StanceDance c = new StanceDance();
        cardList.add(new OctoChoiceCard("octo:OctoBerserk", this.name, ChampMod.makeCardPath("OctoStanceBerserker.png"), c.EXTENDED_DESCRIPTION[0]));
        cardList.add(new OctoChoiceCard("octo:OctoDefense", this.name, ChampMod.makeCardPath("OctoStanceDefensive.png"), c.EXTENDED_DESCRIPTION[1]));
        cardList.add(new OctoChoiceCard("octo:OctoGladiat", this.name, ChampMod.makeCardPath("OctoStanceGladiator.png"), c.EXTENDED_DESCRIPTION[2]));
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
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}