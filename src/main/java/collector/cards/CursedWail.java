package collector.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class CursedWail extends AbstractCollectorCard {
    public final static String ID = makeID(CursedWail.class.getSimpleName());
    // intellij stuff skill, all_enemy, uncommon, , , , , 6, 2

    public CursedWail() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 9;
        baseSecondMagic = secondMagic = 1;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }

        forAllMonstersLiving(q -> {
            applyToEnemy(q, new StrengthPower(q, -magicNumber));
            if (!q.hasPower(ArtifactPower.POWER_ID)) {
                applyToEnemy(q, new GainStrengthPower(q, magicNumber));
            }
        });

        forAllMonstersLiving(q -> {
            if (isAfflicted(q)) {
                applyToEnemy(q, new StrengthPower(q, secondMagic*-1));
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractMonster m : getEnemies()) {
            if (m.hasPower(VulnerablePower.POWER_ID) && m.hasPower(WeakPower.POWER_ID)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}