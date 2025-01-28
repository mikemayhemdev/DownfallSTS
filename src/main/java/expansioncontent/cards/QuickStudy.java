package expansioncontent.cards;

import automaton.AutomatonChar;
import champ.ChampChar;
import collector.CollectorChar;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.expansionContentMod;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;
import theHexaghost.TheHexaghost;

import java.util.ArrayList;
import java.util.Collections;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class QuickStudy extends AbstractExpansionCard {

    public final static String ID = makeID("QuickStudy");

    private ArrayList<AbstractCard> getList() {
        ArrayList<AbstractCard> myList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.rarity != CardRarity.SPECIAL && q.hasTag(expansionContentMod.STUDY)) {

                if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                    if(q.cardID.equals(SuperPrepareCrush.ID)){continue;}
                }
                if (AbstractDungeon.player instanceof TheHexaghost ) {
                    if(q.cardID.equals(Hexaburn.ID)){continue;}
                }
                if (AbstractDungeon.player instanceof GuardianCharacter ) {
                    if(q.cardID.equals(GuardianWhirl.ID)){continue;}
                }
                if (AbstractDungeon.player instanceof ChampChar ) {
                    if(q.cardID.equals(LastStand.ID)){continue;}
                }
                if (AbstractDungeon.player instanceof AutomatonChar ) {
                    if(q.cardID.equals(HyperBeam.ID)){continue;}
                }
                if (AbstractDungeon.player instanceof CollectorChar ) {
                    if(q.cardID.equals(YouAreMine.ID)){continue;}
                }

                AbstractCard r = q.makeCopy();
                if (upgraded) {
                    r.upgrade();
                }
                r.freeToPlayOnce = true;
                myList.add(r);
            }
        }
        return myList;
    }

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> dupeListForPrev = new ArrayList<>();

    public QuickStudy() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_skill.png", "expansioncontentResources/images/1024/bg_boss_skill.png");
        this.exhaust = true;
        loadJokeCardImage(this, "QuickStudy.png");
        //this.tags.add(expansionContentMod.STUDY);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> selectionsList = new ArrayList<>();
        ArrayList<AbstractCard> cardsList = getList();
        Collections.shuffle(cardsList, AbstractDungeon.cardRandomRng.random);
        for (int i = 0; i < 3; i++) {
            selectionsList.add(cardsList.get(i));
        }

        atb(new SelectCardsCenteredAction(selectionsList, 1, EXTENDED_DESCRIPTION[0], (cards) -> {
            addToTop(new MakeTempCardInHandAction(cards.get(0), true));
        }));
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            for (AbstractCard q : this.dupeListForPrev) {
                q.upgrade();
            }
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void update() {
        super.update();
        if (dupeListForPrev.isEmpty()) {
            dupeListForPrev.addAll(getList());
        }
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (dupeListForPrev.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = dupeListForPrev.get(previewIndex);
                }
                if (previewIndex == dupeListForPrev.size() - 1) {
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