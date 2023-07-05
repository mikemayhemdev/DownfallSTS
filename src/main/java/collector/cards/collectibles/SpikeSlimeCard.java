package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class SpikeSlimeCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SpikeSlimeCard.class.getSimpleName());
    // intellij stuff attack, enemy, common, 15, 5, , , , 

    public SpikeSlimeCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 13;
        cardsToPreview = new Slimed();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new MakeTempCardInDiscardAction(new Slimed(), 1));
    }

    public void upp() {
        upgradeDamage(5);
    }
}