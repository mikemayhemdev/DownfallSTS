package guardian.cards;


import automaton.FunctionHelper;
import automaton.cards.Batch;
import automaton.cards.Debug;
import automaton.cards.Decompile;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.CompilePackageAction;
import guardian.patches.AbstractCardEnum;

import java.util.ArrayList;

import static guardian.GuardianMod.makeBetaCardPath;

public class CompilePackage extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("CompilePackage");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/compilePackage.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 0;

    //TUNING CONSTANTS
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();
    public static final String[] TEXT;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
    }

    public CompilePackage() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.exhaust = true;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();

        //cardsList.add(new PackageDefect());
        this.tags.add(CardTags.HEALING);
        cardsList.add(new PackageDefect());
        cardsList.add(new PackageWalker());
        cardsList.add(new PackageSphere());
        cardsList.add(new PackageShapes());
        cardsList.add(new PackageSentry());
        cardsList.add(new PackageDonuDeca());
        cardsList.add(new PackageAutomaton());
        this.tags.add(CardTags.HEALING);
        //MultiCardPreview.add(this, new PackageDefect(), new PackageWalker(), new PackageSphere(), new PackageShapes(), new PackageSentry(), new PackageDonuDeca(), new PackageAutomaton());

        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("CompilePackage.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        AbstractDungeon.actionManager.addToBottom(new CompilePackageAction(this.upgraded));

    }

    public AbstractCard makeCopy() {
        return new CompilePackage();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
            MultiCardPreview.multiCardPreview.get(this).forEach(AbstractCard::upgrade);
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = TEXT[5];
        return AbstractDungeon.player.hasEmptyOrb();
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
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