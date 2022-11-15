package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;
import static collector.util.Wiz.forAllMonstersLiving;

public class Taunt extends AbstractChampCard {

    public final static String ID = makeID("Taunt");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    public Taunt() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 1;
        loadJokeCardImage(this, "Taunt.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            forAllMonstersLiving(q -> applyToEnemy(q, autoWeak(q, 1)));
        } else {
            applyToEnemy(m, autoWeak(m, 1));
        }
        combo();
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        target = CardTarget.ALL_ENEMY;
    }
}