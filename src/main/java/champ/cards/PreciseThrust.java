package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class PreciseThrust extends AbstractChampCard {

    public final static String ID = makeID("PreciseThrust");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 6;
    private static final int BLOCK = 6;

    public PreciseThrust() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOBERSERKER);
        postInit();
        loadJokeCardImage(this, "PreciseThrust.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (dcombo()) {
            blck();
        }
        if (bcombo()){
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = (bcombo() || dcombo()) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}