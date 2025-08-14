package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.GashCawAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import static awakenedOne.AwakenedOneMod.*;

public class Caw extends AbstractAwakenedCard {
    public final static String ID = makeID(Caw.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Caw() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 3;
        this.baseMagicNumber = 3;
        this.tags.add(AwakenedOneMod.CHANT);
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(Caw.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("VO_CULTIST_1A", .3f);
        if (m != null) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);

        if (isTrig_chant()) {
            chant();
        }
    }

    @Override
    public void chant() {
        this.addToBot(new GashCawAction(this, this.magicNumber));
        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}