package collector.cards;

import collector.CollectorCollection;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.makeInHand;

public class Darkstorm extends AbstractCollectorCard {
    public final static String ID = makeID(Darkstorm.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , 4, 2

    public Darkstorm() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        cardsToPreview = new Blightning();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        makeInHand(new Blightning());
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (int i = 0; i < magicNumber; i++)
                    CollectorCollection.combatCollection.addToRandomSpot(new Blightning());
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}