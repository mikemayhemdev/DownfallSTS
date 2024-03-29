package gremlin.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.LimitBreakAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;
import com.megacrit.cardcrawl.vfx.FlameAnimationEffect;

import static gremlin.GremlinMod.MAD_GREMLIN;

public class RageBreak extends AbstractGremlinCard {
    public static final String ID = getID("RageBreak");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/rage_break.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 2;
    private static final int BLOCK = 5;

    public RageBreak()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.exhaust = true;
        this.tags.add(MAD_GREMLIN);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "RageBreak.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlameAnimationEffect(p.hb)));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
        AbstractDungeon.actionManager.addToBottom(new LimitBreakAction());
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

