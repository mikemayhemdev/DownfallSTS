package downfall.cards.curses;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.ScatterbrainedAction;
import downfall.downfallMod;

public class Scatterbrained extends CustomCard {
    public static final String ID = downfallMod.makeID("Scatterbrained");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/scatterbrained.png");

    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    private boolean auto_used_end_turn = false;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Scatterbrained() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);
        this.exhaust = false;
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        auto_used_end_turn = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!auto_used_end_turn) this.exhaust = true; // only exhaust when the player plays the card, not when auto triggered at end turn
        if (this.dontTriggerOnUseCard) {
            this.addToBot(new MakeTempCardInDiscardAction( new Scatterbrained(),1) );
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        auto_used_end_turn = true;
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override //zhs card text thing
    public void initializeDescriptionCN() {
        super.initializeDescriptionCN();
        if(Settings.language == Settings.GameLanguage.ZHS && this.description!=null && this.description.size()>=1 ) {
            for(int i = 0; i < this.description.size(); i++){
                if( this.description.get(i).text.equals("，") ){
                    StringBuilder sb = new StringBuilder();
                    this.description.get(i-1).text = sb.append(this.description.get(i-1).text).append("，").toString();
                    this.description.remove(i);
                }
            }
        }
    }

//    public void triggerWhenDrawn() {
//        if (!AbstractDungeon.actionManager.turnHasEnded) {
//            this.addToBot(new ScatterbrainedAction());
//        }
//    }

    public AbstractCard makeCopy() {
        return new Scatterbrained();
    }

    public void upgrade() {
    }
}

