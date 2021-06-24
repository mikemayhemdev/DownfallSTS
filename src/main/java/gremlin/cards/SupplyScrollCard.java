package gremlin.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
import gremlin.powers.PolishPower;

import java.util.ArrayList;

import static gremlin.relics.SupplyScroll.SUPPLY;

public class SupplyScrollCard extends CustomCard {
    public static final String ID = "Gremlin:SupplyScrollCard";
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/supply_scroll.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public SupplyScrollCard() {
        super(ID, NAME, GremlinMod.getResourcePath(IMG_PATH), COST, strings.DESCRIPTION, TYPE,
                CardColor.COLORLESS, RARITY, TARGET);

        cardsList.add(new Shiv());
        cardsList.add(new Ward());
        this.cardsToPreview = new Shiv();
        this.exhaust = true;

        this.baseMagicNumber = this.magicNumber = SUPPLY;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int shivs = AbstractDungeon.cardRandomRng.random(0, magicNumber);
        if (shivs > 0) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), shivs));
        }
        if (shivs < magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Ward(), magicNumber - shivs));
        }
    }

    @Override
    public void upgrade() {
        upgradeName();
        upgradeMagicNumber(2);
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

