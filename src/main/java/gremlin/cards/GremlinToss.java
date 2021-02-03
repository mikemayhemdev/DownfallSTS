package gremlin.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GremlinToss extends AbstractGremlinCard {
    private static final String ID = getID("GremlinToss");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/gremlin_toss.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_BONUS = 4;

    public GremlinToss()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded) {
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p, p, magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        if(upgraded){
            this.baseDamage = AbstractDungeon.player.currentBlock +
                    TempHPField.tempHp.get(AbstractDungeon.player) +
                    magicNumber;
        }else{
            this.baseDamage = AbstractDungeon.player.currentBlock + TempHPField.tempHp.get(AbstractDungeon.player);
        }
        super.applyPowers();
        if(upgraded)
        {
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = strings.DESCRIPTION;
        }
        this.rawDescription += strings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        if(upgraded)
        {
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = strings.DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(final AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if(upgraded)
        {
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = strings.DESCRIPTION;
        }
        this.rawDescription += strings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
