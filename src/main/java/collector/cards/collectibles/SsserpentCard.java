package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class SsserpentCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SsserpentCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 20, 6, , , , 

    public SsserpentCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 20;
        cardsToPreview = new Doubt();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        makeInHand(new Doubt());
    }

    public void upp() {
        upgradeDamage(6);
    }
}