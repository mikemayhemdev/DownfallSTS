package collector.cards.collectibles;

import collector.cards.Ember;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class OrbWalkerCard extends AbstractCollectibleCard {
    public final static String ID = makeID(OrbWalkerCard.class.getSimpleName());
    // intellij stuff attack, enemy, common, 14, 4, , , 2, 1

    public OrbWalkerCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 14;
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Ember();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        makeInHand(new Ember(), magicNumber);
    }

    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}