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
import gremlin.actions.ShivPerCardPlayedAction;

public class SecondVolley extends AbstractGremlinCard {
    private static final String ID = getID("SecondVolley");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/second_volley.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 2;

    public SecondVolley()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ShivPerCardPlayedAction(upgraded));
        if(upgraded){
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
        }
        else {
            this.rawDescription = strings.DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if(upgraded){
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
        }
        else {
            this.rawDescription = strings.DESCRIPTION;
        }
        this.rawDescription = this.rawDescription + strings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription += strings.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.rawDescription += strings.EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        if(upgraded){
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
        }
        else {
            this.rawDescription = strings.DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
