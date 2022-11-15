package champ.cards;

import champ.ChampMod;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.gladiatorOpen;
import static champ.ChampMod.loadJokeCardImage;

public class BattleCry extends AbstractChampCard {

    public final static String ID = makeID("BattleCry");

    //stupid intellij stuff attack, enemy, rare

    public BattleCry() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        tags.add(ChampMod.OPENERGLADIATOR);
        tags.add(ChampMod.OPENER);
        baseMagicNumber = magicNumber = 2;

        loadJokeCardImage(this, "ShieldThrow.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));

        gladiatorOpen();

        addToBot(new AbstractGameAction() {
            public void update() {
                if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
                    ((AbstractChampStance) AbstractDungeon.player.stance).upgradeStance();
                }
                this.isDone = true;
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}