package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class Retaliation extends AbstractAwakenedCard {
    public final static String ID = makeID(Retaliation.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Retaliation() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 7;
        baseBlock = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (p.currentHealth * 2 < p.maxHealth) {
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
            } else {
                this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
            }
            this.addToBot(new ApplyPowerAction(p, p, new FlameBarrierPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.player.currentHealth * 2 < AbstractDungeon.player.maxHealth ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeBlock(3);
    }
}