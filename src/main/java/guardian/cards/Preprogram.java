package guardian.cards;


import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.PreprogramAction;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;

public class Preprogram extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("Preprogram");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/preprogram.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;

    //TUNING CONSTANTS
    private static final int COUNT = 5;
    private static final int UPGRADECOUNT = 3;
    private static final int SOCKETS = 1;
    private static final boolean SOCKETSAREAFTER = true;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Preprogram() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseMagicNumber = this.magicNumber = COUNT;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("Preprogram.png"));

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        if (!(AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty())) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
            }

            AbstractDungeon.actionManager.addToBottom(new PreprogramAction(this.magicNumber));
        }

        super.useGems(p, m);
    }

    public AbstractCard makeCopy() {
        return new Preprogram();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.upgradeMagicNumber(UPGRADECOUNT);
        }
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

    @Override //zhs card text thing
    public void initializeDescriptionCN() {
        super.initializeDescriptionCN();
        if(Settings.language == Settings.GameLanguage.ZHS && this.description!=null && this.description.size()>=1 ) {
            for(int i=0;i<this.description.size();i++){
                if(this.description.get(i).text.equals("，")){
                    StringBuilder sb = new StringBuilder();
                    this.description.get(i-1).text = sb.append(this.description.get(i-1).text).append("，").toString();
                    this.description.remove(i);
                }
            }
        }
    }
}


