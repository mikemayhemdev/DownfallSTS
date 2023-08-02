/*
package collector.cards;

import collector.powers.DoomPower;
import collector.powers.LanternFlarePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class LanternFlare extends AbstractCollectorCard {
    public final static String ID = makeID(LanternFlare.class.getSimpleName());
    // intellij stuff attack, all_enemy, uncommon, 12, 3, , , 12, 3

    public LanternFlare() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 12;
        baseSecondMagic = secondMagic = 4;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new DoomPower(m, magicNumber));
        applyToEnemy(m, new LanternFlarePower(m, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(3);
        upgradeSecondMagic(1);
    }
}

 */