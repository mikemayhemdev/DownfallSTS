package automaton.cards;

import automaton.FunctionHelper;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

public class RecursiveStrike extends AbstractBronzeCard {

    public final static String ID = makeID("RecursiveStrike");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 4;

    public RecursiveStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 4;
        baseAuto = auto = 2;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < auto; i++) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.ORANGE, Color.WHITE), 0.1F));
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        }
    }

    @Override
    public void atTurnStart() {
        if (FunctionHelper.held != null) {
            if (FunctionHelper.held.contains(this)) {
                baseDamage += magicNumber;
                damage += magicNumber;
                superFlash();
                FunctionHelper.genPreview();
            }
        }
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}