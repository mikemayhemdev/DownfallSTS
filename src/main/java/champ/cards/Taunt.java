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

public class Taunt extends AbstractChampCard {

    public final static String ID = makeID("Taunt");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    public Taunt() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        if (upgraded) {
            for (AbstractMonster q : monsterList()) {
                applyToEnemy(q, autoWeak(q, this.magicNumber));
            }
        } else {
            applyToEnemy(m, autoWeak(m, this.magicNumber));
        }

        ArrayList<String> validStances = new ArrayList<>();



        if (!(p.stance instanceof DefensiveStance)) validStances.add(DefensiveStance.STANCE_ID);
        if (!(p.stance instanceof GladiatorStance)) validStances.add(GladiatorStance.STANCE_ID);
        if (!(p.stance instanceof BerserkerStance)) validStances.add(BerserkerStance.STANCE_ID);

        Collections.shuffle(validStances);

        switch (validStances.get(0)){
            case DefensiveStance.STANCE_ID: defenseOpen(); break;
            case GladiatorStance.STANCE_ID: gladOpen(); break;
            case BerserkerStance.STANCE_ID: berserkOpen(); break;
        }

    }

    public void upp() {
        target = CardTarget.ALL_ENEMY;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}