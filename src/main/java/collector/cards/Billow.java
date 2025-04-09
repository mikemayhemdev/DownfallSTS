package collector.cards;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class Billow extends AbstractCollectorCard {
    public final static String ID = makeID(Billow.class.getSimpleName());
    // intellij stuff skill, self, common, , , 12, , 1, 1

    public Billow() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 18;
        cardsToPreview = new Bellow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new AddCopyNextTurnPower(new Bellow()));
    }

    public void upp() {
        upgradeBlock(5);
    }
}