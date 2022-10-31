package collector.cards.CollectorCards.Skills;

import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend extends AbstractCollectorCard {
    public final static String ID = makeID("Defend");

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF, CollectorCardSource.FRONT);
        FrontBlock = FrontBaseBlock = douBlock = douBaseBlock = baseBlock = 5;
        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeFrontBlock(3);
        upgradeTorchHeadBlock(3);
    }
}
