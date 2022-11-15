package champ.cards;

import champ.ChampMod;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import static champ.ChampMod.loadJokeCardImage;

public class StandYourGround extends AbstractChampCard {

    public final static String ID = makeID("StandYourGround");

    //stupid intellij stuff attack, enemy, rare

    public StandYourGround() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        //  tags.add(ChampMod.FINISHER);
        baseBlock = block = 10;
        tags.add(ChampMod.OPENERDEFENSIVE);
        tags.add(ChampMod.OPENER);
       
        loadJokeCardImage(this, "ShieldThrow.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

        defenseOpen();
        if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
            ((AbstractChampStance) AbstractDungeon.player.stance).upgradeStance();
        }
    }

    public void upp() {
        upgradeBlock(4);
    }
}