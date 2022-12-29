package gremlin.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;

import java.util.ArrayList;

import static gremlin.GremlinMod.SNEAKY_GREMLIN;

public class GremlinArms extends AbstractGremlinCard {
    public static final String ID = getID("GremlinArms");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/gremlin_arms.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BONUS = 1;

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public GremlinArms()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        cardsList.add(new Shiv());
        cardsList.add(new Ward());

        this.cardsToPreview = new Shiv();
        this.tags.add(SNEAKY_GREMLIN);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "GremlinArms.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        for(int i = 0; i < magicNumber; i++){
            int shiv = AbstractDungeon.cardRandomRng.random(0, 1);
            if (shiv == 1)
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), 1));
            else
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Ward(), 1));
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

    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (cardsList.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = cardsList.get(previewIndex);
                }
                if (previewIndex == cardsList.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }
}

