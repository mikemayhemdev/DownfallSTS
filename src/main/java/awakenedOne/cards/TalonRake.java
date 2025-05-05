package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class TalonRake extends AbstractAwakenedCard {
    public final static String ID = makeID(TalonRake.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public TalonRake() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);

        if (!upgraded) {
            atb(new ConjureAction(false));
        }

        if (upgraded) {
            atb(new ConjureAction(true));
        }
    }

    public void upp() {
    }
}