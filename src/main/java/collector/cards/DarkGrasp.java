package collector.cards;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelfTop;

public class DarkGrasp extends AbstractCollectorCard implements OnPyreCard {
    public final static String ID = makeID(DarkGrasp.class.getSimpleName());
    // intellij stuff skill, self, common, , , 7, 3, , 

    public DarkGrasp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void onPyred(AbstractCard card) {
        applyToSelfTop(new AddCopyNextTurnPower(card));
    }

    public void upp() {
        upgradeBlock(3);
    }
}