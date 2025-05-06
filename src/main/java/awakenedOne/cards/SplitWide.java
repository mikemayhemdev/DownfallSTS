package awakenedOne.cards;

import awakenedOne.relics.KTRibbon;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.ui.AwakenButton.awaken;

public class SplitWide extends AbstractAwakenedCard {
    public final static String ID = makeID(SplitWide.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    boolean chant = false;

    public SplitWide() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 6;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagic = 2;
        this.secondMagic = this.baseSecondMagic;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int i;
        if (Settings.FAST_MODE) {
            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));

            for(i = 0; i < 5; ++i) {
                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        } else {
            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));

            for(i = 0; i < 5; ++i) {
                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        }

        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);

        if (this.chant) {
            checkOnChant();
        }

        if ((!this.chant) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                checkOnChant();
                awaken(1);
            }
        }

    }

    @Override
    public void chant() {
        checkOnChant();
    }

    public void triggerWhenDrawn() {
        this.chant = false;
    }

    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.type == CardType.POWER && AbstractDungeon.player.hand.contains((AbstractCard)this))
            this.chant = true;
    }

    @Override
    public void onMoveToDiscard() {
        this.chant = false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    //heavy blade code

    public void applyPowers() {

        boolean ischanting;

        ischanting = false;

        if (this.chant) {
            ischanting = true;
        }

        if (!this.chant) {
            if (AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
                if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                    ischanting = true;
                }
            }
        }

        if (ischanting == false) {
            AbstractPower strength = AbstractDungeon.player.getPower("Strength");
            if (strength != null) {
                strength.amount *= this.magicNumber;
            }

            super.applyPowers();
            if (strength != null) {
                strength.amount /= this.magicNumber;
            }
        }


        if (ischanting == true) {
            AbstractPower strength = AbstractDungeon.player.getPower("Strength");
            if (strength != null) {
                strength.amount *= this.magicNumber+secondMagic;
            }

            super.applyPowers();
            if (strength != null) {
                strength.amount /= this.magicNumber+secondMagic;
            }
        }

    }

    public void calculateCardDamage(AbstractMonster mo) {

        boolean ischanting;

        ischanting = false;

        if (this.chant) {
            ischanting = true;
        }

        if (!this.chant) {
            if (AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
                if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                    ischanting = true;
                }
            }
        }

        if (ischanting == false) {
            AbstractPower strength = AbstractDungeon.player.getPower("Strength");
            if (strength != null) {
                strength.amount *= this.magicNumber;
            }

            super.applyPowers();
            if (strength != null) {
                strength.amount /= this.magicNumber;
            }
        }


        if (ischanting == true) {
            AbstractPower strength = AbstractDungeon.player.getPower("Strength");
            if (strength != null) {
                strength.amount *= (this.magicNumber+secondMagic);
            }

            super.applyPowers();
            if (strength != null) {
                strength.amount /= (this.magicNumber+secondMagic);
            }
        }

    }







    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeSecondMagic(1);
    }
}