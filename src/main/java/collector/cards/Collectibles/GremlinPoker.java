package collector.cards.Collectibles;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static collector.CollectorMod.makeID;
public class GremlinPoker extends AbstractCollectibleCard {
    public final static String ID = makeID("GremlinPoker");

    public GremlinPoker() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        AbstractCard card = this;
        atb(new AbstractGameAction() {
            public void update() {
             CollectorMod.PerpetualEffect(card);
                this.isDone = true;
            }});
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}
