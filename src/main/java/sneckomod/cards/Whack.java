package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import sneckomod.SneckoMod;

public class Whack extends AbstractSneckoCard {

    public static final String ID = makeID("Whack");

    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int COST = 2;

    public Whack() {
        super(ID, COST, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "Whack.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        boolean playedOffClassCard = AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .anyMatch(card -> card.color != this.color);

        if (playedOffClassCard) {
            int unblockedDamage = Math.max(damage - m.currentBlock, 0);
            addToBot(new GainBlockAction(p, p, unblockedDamage));
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
