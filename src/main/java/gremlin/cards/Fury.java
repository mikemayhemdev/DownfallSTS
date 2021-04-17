package gremlin.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static gremlin.GremlinMod.MAD_GREMLIN;

public class Fury extends AbstractGremlinCard {
    public static final String ID = getID("Fury");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/fury.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 3;
    private static final int POWER = 7;
    private static final int UPGRADE_BONUS = 2;

    private int prevDiscount = 0;

    public Fury()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.tags.add(MAD_GREMLIN);
        setBackgrounds();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void applyPowers() {
        this.costForTurn += this.prevDiscount;

        super.applyPowers();
        if (!AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            if (this.costForTurn == COST) {
                this.isCostModifiedForTurn = false;
            }
            return;
        }
        if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount < 0) {
            if (this.costForTurn == COST) {
                this.isCostModifiedForTurn = false;
            }
            return;
        }

        this.prevDiscount = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount / 2;

        if (this.costForTurn - this.prevDiscount < 0) this.prevDiscount = this.costForTurn;

        this.costForTurn = this.costForTurn - this.prevDiscount;
        if (this.costForTurn != COST) {
            this.isCostModifiedForTurn = true;
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.prevDiscount = 0;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        this.prevDiscount = 0;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }
    }
}
