package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class RainOfDice extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("RainOfDice");

    private static final int BASE_DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 2;

    public RainOfDice() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = BASE_DAMAGE;
        exhaust = true;
        SneckoMod.loadJokeCardImage(this, "RainOfDice.png");
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(final AbstractMonster m) {
        int originalBaseDamage = baseDamage;
        super.calculateCardDamage(m);
        baseDamage = originalBaseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        addToBot(new SelectCardsInHandAction(1, BaseMod.getKeywordProper("sneckomod:muddle"),
                (AbstractCard c) -> true,
                (cards) -> {
                    for (AbstractCard card : cards) {
                        addToBot(new MuddleAction(card));
                    }
                }
        ));
        AbstractCard g = new RainOfDice();
        if (this.upgraded) {
            g.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(g));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
