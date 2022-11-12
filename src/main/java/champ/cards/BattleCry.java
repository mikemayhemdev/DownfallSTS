package champ.cards;

import basemod.devcommands.draw.Draw;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.gladiatorOpen;
import static champ.ChampMod.loadJokeCardImage;

public class BattleCry extends AbstractChampCard {

    public final static String ID = makeID("BattleCry");

    //stupid intellij stuff attack, enemy, rare

    public BattleCry() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        //  tags.add(ChampMod.FINISHER);
        tags.add(ChampMod.OPENERGLADIATOR);
        tags.add(ChampMod.OPENER);
        postInit();
        loadJokeCardImage(this, "ShieldThrow.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        atb(new DrawCardAction(magicNumber));

        gladiatorOpen();
        //TODO "upgrade stance"

    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}