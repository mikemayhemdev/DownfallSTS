package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class ImprovisedAttack extends AbstractSneckoCard {

    public final static String ID = makeID("ImprovisedAttack");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    public static AbstractCard storage;

    public ImprovisedAttack() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        AbstractCard q = SneckoMod.getOffClassCardMatchingPredicate(c -> c.type == CardType.ATTACK);
        makeInHand(q);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                addToTop(new MuddleAction(storage));
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}