package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import sneckomod.SneckoMod;

public class RoundaboutSwing extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("RoundaboutSwing");

    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MAGIC = 2;

    public RoundaboutSwing() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "RoundaboutSwing.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        this.addToBot(new PutOnDeckAction(p, p, 1, false));
        applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
