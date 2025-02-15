package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Wallop;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.HoleUp;
import sneckomod.SneckoMod;

public class Whack extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Whack");

    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int COST = 1;

    public Whack() {
        super(ID, COST, AbstractCard.CardType.ATTACK, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        baseDamage = DAMAGE;
        this.exhaust = true;
        this.cardsToPreview = new Wallop();
        SneckoMod.loadJokeCardImage(this, "Whack.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractCard g = new Wallop();
        if (this.upgraded) {
            g.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(g));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            AbstractCard q = new Wallop();
            q.upgrade();
            cardsToPreview = q;
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
