package collector.cards;

import collector.powers.NextCollectedTwicePower;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class DoubleTrouble extends AbstractCollectorCard {
    public final static String ID = makeID(DoubleTrouble.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 6, 3, , 

    public DoubleTrouble() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 6;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AnimateHopAction(p));
        blck();
        applyToSelf(new NextCollectedTwicePower());
    }

    public void upp() {
        upgradeBlock(3);
    }
}