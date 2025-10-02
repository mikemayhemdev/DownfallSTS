package downfall.cards.curses;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import sneckomod.OffclassHelper;


public class Malfunctioning extends CustomCard {
    public static final String ID = downfallMod.makeID("Malfunctioning");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/malfunctioning.png");
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    //Flawed flawed
    public Malfunctioning() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);
        this.exhaust = true;
        tags.add(downfallMod.DOWNFALL_CURSE);
        this.cardsToPreview = new Wound();
        //this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        this.flash();
        this.superFlash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(),1));
    }

    public AbstractCard makeCopy() {
        return new Malfunctioning();
    }

    public void upgrade() {
    }

    @Override //zhs card text thing
    public void initializeDescriptionCN() {
        super.initializeDescriptionCN();
        if((Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) && this.description!=null && this.description.size()>=1 ) {
            for(int i=0; i < this.description.size(); i++){
                if(this.description.get(i).text.equals("，")){
                    StringBuilder sb = new StringBuilder();
                    this.description.get(i-1).text = sb.append(this.description.get(i-1).text).append("，").toString();
                    this.description.remove(i);
                }
            }
        }
    }
}


