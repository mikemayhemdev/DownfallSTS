package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class PerpetualSerpent extends AbstractSneckoCard {

    public static final String ID = makeID("PerpetualSerpent");

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 5;
    private static final int MAGIC = 2;

    public PerpetualSerpent() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "PerpetualSerpent.png");
        this.tags.add(SneckoMod.OVERFLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        if (isOverflowActive(this)) {
            addToBot(new GainEnergyAction(MAGIC));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}
