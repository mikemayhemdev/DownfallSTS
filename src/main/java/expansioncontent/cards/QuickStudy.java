package expansioncontent.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import expansioncontent.expansionContentMod;

import java.util.ArrayList;
import java.util.Collections;

public class QuickStudy extends AbstractExpansionCard {

    public final static String ID = makeID("QuickStudy");
    public String[] NAMES = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").NAMES;
    public String[] TEXT = CardCrawlGame.languagePack.getCharacterString("downfall:OctoChoiceCards").TEXT;

    //stupid intellij stuff SKILL, SELF, RARE

    public static ArrayList<AbstractCard> allStudyCardsList = new ArrayList<>();
    public static ArrayList<AbstractCard> allStudyCardsListUpgraded = new ArrayList<>();

    private float rotationTimer;
    private int previewIndex;

    public QuickStudy() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_skill.png", "expansioncontentResources/images/1024/bg_boss_skill.png");
        this.exhaust = true;


        if (allStudyCardsList.size() == 0) {
            for (AbstractCard q : CardLibrary.getAllCards()) {
                if (q.hasTag(expansionContentMod.STUDY)) {
                    allStudyCardsList.add(q.makeCopy());
                }
            }
            for (AbstractCard q : allStudyCardsList) {
                q.setCostForTurn(0);
                allStudyCardsListUpgraded.add(q.makeCopy());
            }
            for (AbstractCard q : allStudyCardsListUpgraded) {
                q.setCostForTurn(0);
                q.upgrade();
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> selectionsList = new ArrayList<>();
        if (upgraded) {
            Collections.shuffle(allStudyCardsList);
            for (int i = 0; i < 3; i++) {
                selectionsList.add(allStudyCardsListUpgraded.get(i).makeStatEquivalentCopy());
            }
        } else {
            Collections.shuffle(allStudyCardsListUpgraded);
            for (int i = 0; i < 3; i++) {
                selectionsList.add(allStudyCardsList.get(i).makeStatEquivalentCopy());
            }
        }

        atb(new SelectCardsCenteredAction(selectionsList, 1, EXTENDED_DESCRIPTION[0], (cards) -> {
            addToTop(new MakeTempCardInHandAction(cards.get(0), true));
        }));
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }



    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                if (!upgraded) {
                    rotationTimer = 2F;
                    if (allStudyCardsList.size() == 0) {
                        cardsToPreview = CardLibrary.cards.get("Madness");
                    } else {
                        cardsToPreview = allStudyCardsList.get(previewIndex);
                    }
                    if (previewIndex == allStudyCardsList.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                } else {

                    rotationTimer = 2F;
                    if (allStudyCardsListUpgraded.size() == 0) {
                        cardsToPreview = CardLibrary.cards.get("Madness");
                    } else {
                        cardsToPreview = allStudyCardsListUpgraded.get(previewIndex);
                    }
                    if (previewIndex == allStudyCardsListUpgraded.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        cardsToPreview = null;
    }
}