package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class SneakyGremlinCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SneakyGremlinCard.class.getSimpleName());
    // intellij stuff attack, enemy, common, 10, 5, , , , 

    public SneakyGremlinCard() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        atb(new DrawCardAction(1));
    }

    public void upp() {
        upgradeDamage(4);
    }
}