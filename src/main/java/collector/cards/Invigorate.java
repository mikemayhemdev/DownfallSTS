package collector.cards;

import collector.powers.NextTurnVigorPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Invigorate extends AbstractCollectorCard {
    public final static String ID = makeID(Invigorate.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public Invigorate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }
        applyToSelf(new VigorPower(p, magicNumber));
        applyToSelf(new NextTurnVigorPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}