package expansioncontent.cards;


import automaton.AutomatonChar;
import awakenedOne.AwakenedOneChar;
import champ.ChampChar;
import collector.CollectorChar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.StudyTheSpirePower;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;
import theHexaghost.TheHexaghost;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

import java.util.ArrayList;

public class StudyTheSpire extends AbstractExpansionCard {
    public final static String ID = makeID("StudyTheSpire");

    private ArrayList<AbstractCard> getList() {
        ArrayList<AbstractCard> myList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.rarity != CardRarity.SPECIAL && q.hasTag(expansionContentMod.STUDY)) {

                if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                    if (q.hasTag(expansionContentMod.STUDY_SLIMEBOSS)){continue;}
                } else if (AbstractDungeon.player instanceof TheHexaghost) {
                    if(q.hasTag(expansionContentMod.STUDY_HEXAGHOST)){continue;}
                } else if (AbstractDungeon.player instanceof GuardianCharacter) {
                    if(q.hasTag(expansionContentMod.STUDY_GUARDIAN)){continue;}
                } else if (AbstractDungeon.player instanceof ChampChar) {
                    if(q.hasTag(expansionContentMod.STUDY_CHAMP)){continue;}
                } else if (AbstractDungeon.player instanceof AutomatonChar) {
                    if(q.hasTag(expansionContentMod.STUDY_AUTOMATON)){continue;}
                } else if (AbstractDungeon.player instanceof CollectorChar) {
                    if(q.hasTag(expansionContentMod.STUDY_COLLECTOR)){continue;}
                } else if (AbstractDungeon.player instanceof AwakenedOneChar) {
                    if(q.hasTag(expansionContentMod.STUDY_AWAKENEDONE)){continue;}
                }

                AbstractCard r = q.makeCopy();
                if (upgraded) {
                    r.upgrade();
                }
                myList.add(r);
            }
        }
        return myList;
    }

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> dupeListForPrev = new ArrayList<>();

    public StudyTheSpire() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_power.png", "expansioncontentResources/images/1024/bg_boss_power.png");
        loadJokeCardImage(this, "StudyTheSpire.png");
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
            for (AbstractCard q : this.dupeListForPrev) {
                q.upgrade();
            }
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