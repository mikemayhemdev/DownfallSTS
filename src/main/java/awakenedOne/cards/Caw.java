package awakenedOne.cards;

import awakenedOne.actions.GashCawAction;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.ui.AwakenButton.awaken;

public class Caw extends AbstractAwakenedCard {
    public final static String ID = makeID(Caw.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Caw() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 3;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("VO_CULTIST_1A", .3f);
        if (m != null) {
            this.addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);

        if (Wiz.isChantActive()) {
           chant();
        }

        if ((!Wiz.isChantActive()) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                chant();
                awaken(1);
            }
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
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}