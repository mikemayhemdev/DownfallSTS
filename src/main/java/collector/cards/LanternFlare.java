
package collector.cards;

import collector.effects.ColoredSanctityEffect;
import collector.powers.DoomPower;
import collector.powers.LanternFlarePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class LanternFlare extends AbstractCollectorCard {
    public final static String ID = makeID(LanternFlare.class.getSimpleName());
    // intellij stuff attack, all_enemy, uncommon, 12, 3, , , 12, 3

    public LanternFlare() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 12;
        baseSecondMagic = secondMagic = 3;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new ColoredSanctityEffect(m.hb.cX, m.hb.cY, Color.CHARTREUSE.cpy())));
        applyToEnemy(m, new DoomPower(m, magicNumber));
        applyToEnemy(m, new LanternFlarePower(m, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(3);
        upgradeSecondMagic(1);
    }
}

