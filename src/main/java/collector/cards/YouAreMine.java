package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class YouAreMine extends AbstractCollectorCard {
    public final static String ID = makeID(YouAreMine.class.getSimpleName());
    // intellij stuff skill, enemy, rare, , , , , 4, 2

    public YouAreMine() {
        super(ID, 2, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseSecondMagic = secondMagic = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new CollectorCurseEffect(m.hb.cX, m.hb.cY), .2F));
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        applyToEnemy(m, new DoomPower(m, secondMagic));
    }

    public void upp() {
        upgradeSecondMagic(2);
    }
}