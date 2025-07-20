package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.actions.ForTheHexAction;
import collector.powers.AddCopyNextTurnPower;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import java.util.Collections;
import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;
import static collector.util.Wiz.applyToSelfTop;

public class TalonRake extends AbstractAwakenedCard {
    public final static String ID = makeID(TalonRake.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public TalonRake() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;
        this.tags.add(AwakenedOneMod.CHANT);
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(TalonRake.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.FIRE);

        if (isTrig_chant()) {
            this.tags.add(ACTIVECHANT);
            chant();
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void chant() {
        atb(new ConjureAction(false));
        checkOnChant();
    }

    public void upp() {
        upgradeDamage(3);
        //upgradeBaseCost(0);
    }
}