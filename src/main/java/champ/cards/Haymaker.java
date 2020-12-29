package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Haymaker extends AbstractChampCard {

    public final static String ID = makeID("Haymaker");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 4;

    public Haymaker() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
       // tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOGLADIATOR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //if (upgraded) techique();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        applyToEnemy(m, autoWeak(m, 2));
        if (gcombo()) {
            applyToEnemy(m, autoVuln(m, 2));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = gcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(4);
    }
}