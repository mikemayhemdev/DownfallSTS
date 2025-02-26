package collector.cards;

import collector.effects.GreenflameBarrierEffect;
import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Bonfire extends AbstractCollectorCard {
    public final static String ID = makeID(Bonfire.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 20, 5, , 

    public Bonfire() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 12;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new GreenflameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            this.addToBot(new VFXAction(p, new GreenflameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }
        blck();
        applyToSelf(new NextTurnReservePower(1));
    }


    public void upp() {
        upgradeBlock(4);
    }
}