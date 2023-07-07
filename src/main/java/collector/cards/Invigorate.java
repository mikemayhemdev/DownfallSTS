package collector.cards;

import collector.powers.NextTurnVigorPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Invigorate extends AbstractCollectorCard {
    public final static String ID = makeID(Invigorate.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , , 

    public Invigorate() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VigorPower(p, magicNumber));
        applyToSelf(new NextTurnVigorPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}