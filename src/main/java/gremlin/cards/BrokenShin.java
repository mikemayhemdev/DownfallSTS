package gremlin.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.powers.AgonyPower;
import sneckomod.SneckoMod;

public class BrokenShin extends AbstractGremlinCard {
    private static final String ID = getID("BrokenShin");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/broken_shin.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;
    private static final int MAGIC = 4;
    private static final int UPGRADE_BONUS = -1;

    public BrokenShin()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(m.hasPower("Weakened")){
            int amount = m.getPower("Weakened").amount / this.magicNumber;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Weakened"));
            if(amount > 0){
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(m, p, new AgonyPower(m, amount, false), amount));
            }
        }
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
        }
    }
}

