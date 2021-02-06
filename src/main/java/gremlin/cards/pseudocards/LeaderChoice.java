package gremlin.cards.pseudocards;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.cards.AbstractGremlinCard;
import gremlin.patches.Unmovable;

import java.util.ArrayList;


public class LeaderChoice extends AbstractGremlinCard implements Unmovable {
    public static final String ID = getID("LeaderChoice");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/choice.png";

    private static final AbstractCard.CardType TYPE = CardType.STATUS;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 0;

    public LeaderChoice()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.dontTriggerOnUseCard = true;
        this.purgeOnUse = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        ArrayList<AbstractCard> options = new ArrayList<>();
        if(AbstractDungeon.player == null || AbstractDungeon.player.gold > 0) {
            options.add(new CowerChoiceB());
        }
        options.add(new FightChoice());
        addToBot(new ChooseOneAction(options));
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



