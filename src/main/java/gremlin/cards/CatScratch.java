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
import gremlin.GremlinMod;

import static gremlin.GremlinMod.MAD_GREMLIN;

public class CatScratch extends AbstractGremlinCard {
    public static final String ID = getID("CatScratch");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/cat_scratch.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 2;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public CatScratch()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
        this.tags.add(MAD_GREMLIN);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "CatScratch.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractGameAction.AttackEffect[] attacks = {AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL};

        for(int i=0;i<this.magicNumber;i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                    this.damageTypeForTurn), attacks[i%3]));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
