package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;

public class Blunderbus extends AbstractSneckoCard {

    public final static String ID = SneckoMod.makeID("Blunderbus");

    private static final int DAMAGE = 11;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int BASE_HITS = 1;

    public Blunderbus() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "Blunderbus.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int additionalHits = 0;

        if (this.costForTurn >= 3) {
            additionalHits++;
        }

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.costForTurn >= 3 && card != this) {
                additionalHits++;
            }
        }

        int totalHits = BASE_HITS + additionalHits;

        for (int i = 0; i < totalHits; i++) {
            allDmg(AbstractGameAction.AttackEffect.FIRE);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }
}
