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
import gremlin.actions.JeerAction;
import gremlin.actions.JeerOuterAction;

import static gremlin.GremlinMod.FAT_GREMLIN;

public class Jeer extends AbstractGremlinCard {
    public static final String ID = getID("Jeer");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/jeer.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int POWER = 7;
    private static final int UPGRADE_BONUS = 4;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public Jeer()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseDamage = POWER;
        this.baseMagicNumber = MAGIC;
        this.magicNumber = MAGIC;
        this.tags.add(FAT_GREMLIN);
        setBackgrounds();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new JeerOuterAction(m,new DamageInfo(p, this.damage,
                this.damageTypeForTurn),this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
