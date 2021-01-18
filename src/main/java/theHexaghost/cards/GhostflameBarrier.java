package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.GhostFlameBarrierPower;
import theHexaghost.vfx.SpookyFlameBarrier;

public class GhostflameBarrier extends AbstractHexaCard {

    public final static String ID = makeID("GhostflameBarrier");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public GhostflameBarrier() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseBurn = burn = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {// 38
            this.addToBot(new VFXAction(p, new SpookyFlameBarrier(p.hb.cX, p.hb.cY), 0.1F));// 39
        } else {
            this.addToBot(new VFXAction(p, new SpookyFlameBarrier(p.hb.cX, p.hb.cY), 0.5F));// 41
        }
        blck();
        applyToSelf(new GhostFlameBarrierPower(burn));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeBurn(UPG_MAGIC);
        }
    }
}