package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;

public class PowerShot extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("PowerShot");
    private static final int DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 2;

    public PowerShot() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        SneckoMod.loadJokeCardImage(this, "PowerShot.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage to the targeted enemy
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.POWER && AbstractDungeon.player.discardPile.contains(this)) {
            this.addToBot(new DiscardToHandAction(this));
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
