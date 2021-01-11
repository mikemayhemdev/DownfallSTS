package expansioncontent.cards;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import expansioncontent.powers.StudyTheSpirePower;


public class StudyTheSpire extends AbstractExpansionCard {
    public final static String ID = makeID("StudyTheSpire");

    private float rotationTimer;
    private int previewIndex;

    public StudyTheSpire() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_power.png", "expansioncontentResources/images/1024/bg_boss_power.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));
        atb(new VFXAction(p, new IntenseZoomEffect(p.hb.cX, p.hb.cY, false), 0.05F));
        applyToSelf(new StudyTheSpirePower(p, magicNumber, upgraded));
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
                    if (QuickStudy.allStudyCardsList.size() == 0) {
                        cardsToPreview = CardLibrary.cards.get("Madness");
                    } else {
                        cardsToPreview = QuickStudy.allStudyCardsList.get(previewIndex);
                    }
                    if (previewIndex == QuickStudy.allStudyCardsList.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                } else {

                    rotationTimer = 2F;
                    if (QuickStudy.allStudyCardsListUpgraded.size() == 0) {
                        cardsToPreview = CardLibrary.cards.get("Madness");
                    } else {
                        cardsToPreview = QuickStudy.allStudyCardsListUpgraded.get(previewIndex);
                    }
                    if (previewIndex == QuickStudy.allStudyCardsListUpgraded.size() - 1) {
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