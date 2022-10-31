package hermit.cards;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.cards.PackageAutomaton;
import guardian.cards.PackageDonuDeca;
import hermit.HermitMod;
import hermit.actions.FullyLoadedAction;
import hermit.actions.SoundAction;
import hermit.characters.hermit;

import java.util.ArrayList;

import static hermit.HermitMod.*;

public class FullyLoaded extends AbstractDynamicCard {


    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(FullyLoaded.class.getSimpleName());
    public static final String IMG = makeCardPath("fully_loaded.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;



    private static final int COST = 0;
    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    // /STAT DECLARATION/

    public FullyLoaded() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust=true;
        cardsList.add(new Strike_Hermit());
        cardsList.add(new Defend_Hermit());
        loadJokeCardImage(this, "fully_loaded.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playAV(makeID("SPIN"), 1.0f, 1.25f); // Sound Effect
        this.addToBot(new FullyLoadedAction());
        this.addToBot(new SoundAction(makeID("RELOAD")));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            this.selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
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
