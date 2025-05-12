package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.SoulburnOnExhaustPower;

public class GhostflameBarrier extends AbstractHexaCard {
    public final static String ID = makeID("WhisperFromBeyond");

    public GhostflameBarrier() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 12;
        baseBurn = burn = 5;
        HexaMod.loadJokeCardImage(this, "GhostflameBarrier.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new ApplyPowerAction(m, p, new SoulburnOnExhaustPower(m, burn)));

//        if (Settings.FAST_MODE) {// 38
//            this.addToBot(new VFXAction(p, new SpookyFlameBarrier(p.hb.cX, p.hb.cY), 0.1F));// 39
//        } else {
//            this.addToBot(new VFXAction(p, new SpookyFlameBarrier(p.hb.cX, p.hb.cY), 0.5F));// 41
//        }
//        blck();

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBurn(2);
        }
    }

    @Override
    public float getTitleFontSize() {
        if(Settings.language != Settings.GameLanguage.ENG) {
            return -1.0F;
        }else {
            return 19.0F;
        }
    }
}