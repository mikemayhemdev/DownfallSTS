package gremlin.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;
import gremlin.powers.PolishPower;

public class Ward extends CustomCard {
    private static final String ID = "Gremlin:Ward";
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/ward.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 0;
    public static final int BLOCK = 3;
    public static final int UPGRADE_BONUS = 2;

    public Ward()
    {
        super(ID, NAME, GremlinMod.getResourcePath(IMG_PATH), COST, strings.DESCRIPTION, TYPE,
                CardColor.COLORLESS, RARITY, TARGET);

        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hasPower(PolishPower.POWER_ID))) {
            this.baseBlock = (BLOCK + AbstractDungeon.player.getPower(PolishPower.POWER_ID).amount);
        } else {
            this.baseBlock = BLOCK;
        }
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeBlock(UPGRADE_BONUS);
        }
    }
}

