package champ.cards;

import champ.ChampMod;
import champ.actions.AnimateSuplexAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Haymaker extends AbstractChampCard {

    public final static String ID = makeID("Haymaker");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;

    public Haymaker() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOBERSERKER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AnimateSuplexAction(m));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (dcombo())
            applyToEnemy(m, autoWeak(m, 2));
        if (bcombo())
            applyToEnemy(m, autoVuln(m, 2));
    }

    @Override
    public void applyPowers() {
        rawDescription = UPGRADE_DESCRIPTION;
        if (bcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        rawDescription += EXTENDED_DESCRIPTION[0];
        if (dcombo()) rawDescription += "[#5ebf2a]";
        else rawDescription += "*";
        rawDescription += EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() || bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}