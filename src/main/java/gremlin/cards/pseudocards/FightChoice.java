package gremlin.cards.pseudocards;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gremlin.actions.SetCardTargetCoordinatesAction;
import gremlin.cards.AbstractGremlinCard;
import gremlin.characters.GremlinCharacter;

public class FightChoice extends AbstractGremlinCard {
    private static final String ID = getID("FightChoice");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/rage_break.png";

    private static final AbstractCard.CardType TYPE = CardType.STATUS;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;

    private static final int COST = 0;
    private static final int MAGIC = 3;

    public FightChoice()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.dontTriggerOnUseCard = true;

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        onChoseThisOption();
    }

    public void onChoseThisOption(){
        AbstractDungeon.actionManager.addToBottom(new SetCardTargetCoordinatesAction(this, Settings.WIDTH/2.0f - 75f, -1f));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                if(mo instanceof GremlinNob ) {
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(mo, strings.EXTENDED_DESCRIPTION[0]));
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(mo, mo, new StrengthPower(mo, magicNumber), magicNumber));
                }
                if(mo instanceof GremlinLeader) {
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(mo, strings.EXTENDED_DESCRIPTION[1]));
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(mo, mo, new StrengthPower(mo, magicNumber), magicNumber));
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 5));
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter) AbstractDungeon.player).removeCower(true);
        }
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        return true;
    }
}



