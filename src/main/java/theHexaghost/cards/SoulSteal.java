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
import theHexaghost.powers.BurnPower;
import theHexaghost.vfx.ExplosionSmallEffectGreen;

public class SoulSteal extends AbstractHexaCard {

    public final static String ID = makeID("SoulSteal");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON


    public SoulSteal() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBurn = burn = 5;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (m.hasPower(BurnPower.POWER_ID)) {
                    AbstractPower p = m.getPower(BurnPower.POWER_ID);
                    p.flashWithoutSound();// 67
                    addToBot(new VFXAction(new ExplosionSmallEffectGreen(p.owner.hb.cX, p.owner.hb.cY), 0.1F));
                    addToBot(new LoseHPAction(p.owner, p.owner, amount, AbstractGameAction.AttackEffect.FIRE));
                    addToBot(new RemoveSpecificPowerAction(p.owner, p.owner, BurnPower.POWER_ID));
                }
            }
        });
    }

    public void triggerOnGlowCheck() {
        burnGlowCheck();
    }// 68

    public void upgrade() {
        if (!upgraded) {
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}