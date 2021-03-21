package gremlin.cards.pseudocards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import gremlin.actions.LoseRearmostGremlinAction;
import gremlin.actions.SetCardTargetCoordinatesAction;
import gremlin.cards.AbstractGremlinCard;
import gremlin.characters.GremlinCharacter;

public class CowerChoiceB extends AbstractGremlinCard {
    public static final String ID = getID("CowerChoiceB");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/cower.png";

    private static final AbstractCard.CardType TYPE = CardType.STATUS;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;

    private static final int COST = -2;

    public CowerChoiceB()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.dontTriggerOnUseCard = true;
        AlwaysRetainField.alwaysRetain.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new SetCardTargetCoordinatesAction(this, Settings.WIDTH/2.0f - 75f, -1f));
        AbstractDungeon.getCurrRoom().mugged = true;

        AbstractDungeon.actionManager.addToBottom(new LoseRearmostGremlinAction());

        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                if(mo instanceof GremlinLeader) {
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(mo, strings.EXTENDED_DESCRIPTION[0]));
                    break;
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new WaitAction(2f));
        AbstractMonster lastNonLeader = null;
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                if(!(mo instanceof GremlinLeader)) {
                    lastNonLeader = mo;
                }
            }
        }
        if(lastNonLeader != null){
            AbstractDungeon.actionManager.addToBottom(new TalkAction(lastNonLeader, strings.EXTENDED_DESCRIPTION[1]));
        }
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(mo));
            }
        }
        if(AbstractDungeon.player instanceof GremlinCharacter){
            ((GremlinCharacter) AbstractDungeon.player).removeCower(false);
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



