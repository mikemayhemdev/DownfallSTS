package champ.cards;

import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.DefensiveStance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;

import java.util.ArrayList;
import java.util.Collections;

public class Taunt extends AbstractChampCard {

    public final static String ID = makeID("Taunt");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    public Taunt() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ALL_ENEMY);
        tags.add(ChampMod.TECHNIQUE);
        //tags.add(ChampMod.OPENER);
        this.magicNumber = this.baseMagicNumber = 2;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        for (AbstractMonster q : monsterList()) {
            applyToEnemy(q, autoWeak(q, this.magicNumber));
        }



    }



    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "octo:OctoBerserk":
                berserkOpen();
                break;
            case "octo:OctoDefense":
                defenseOpen();
                break;
        }
    }


    public void upp() {
        upgradeMagicNumber(1);
    }
}