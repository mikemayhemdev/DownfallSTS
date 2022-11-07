package collector.cards;

import collector.actions.HealTorchheadAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class Bonfire extends AbstractCollectorCard {
    public final static String ID = makeID(Bonfire.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Bonfire() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Ember();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new MakeTempCardInHandAction(new Ember()));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}