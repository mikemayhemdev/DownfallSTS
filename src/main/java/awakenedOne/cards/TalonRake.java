package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class TalonRake extends AbstractAwakenedCard {
    public final static String ID = makeID(TalonRake.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public TalonRake() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 4;
        //this.tags.add(AwakenedOneMod.CHANT);
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(TalonRake.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.CYAN.cpy(), Color.WHITE)));
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
            this.addToBot(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.CYAN.cpy(), Color.WHITE)));
            dmg(m, AbstractGameAction.AttackEffect.FIRE);
        }
        atb(new ConjureAction(false));
//        if (isTrig_chant()) {
//            this.tags.add(ACTIVECHANT);
//            chant();
//        }
    }

//    public void triggerOnGlowCheck() {
//        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
//    }

    //    @Override
    //    public void chant() {
    //        atb(new ConjureAction(false));
    //        checkOnChant();
    //    }

    public void upp() {
        upgradeDamage(2);
        //upgradeBaseCost(0);
    }
}