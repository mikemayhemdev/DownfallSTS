package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.RetractAction;
import theHexaghost.patches.NoDiscardField;
import theHexaghost.powers.BurnPerTurnPower;
import theHexaghost.powers.BurnPower;
import theHexaghost.powers.LivingBombPower;
import theHexaghost.vfx.ExplosionSmallEffectGreen;

public class SoulSteal extends AbstractHexaCard {

    public final static String ID = makeID("SoulSteal");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON


    public SoulSteal() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBurn = burn = 5;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);
        applyToEnemy(m, new LivingBombPower(m, magicNumber));

    }

    public void upgrade() {
        if (!upgraded) {
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}