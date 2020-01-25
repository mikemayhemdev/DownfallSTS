package guardian.actions;

         import com.megacrit.cardcrawl.actions.GameActionManager;
         import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
         import com.megacrit.cardcrawl.cards.AbstractCard;
         import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
         import com.megacrit.cardcrawl.cards.CardGroup;
         import com.megacrit.cardcrawl.characters.AbstractPlayer;
         import com.megacrit.cardcrawl.core.AbstractCreature;
         import com.megacrit.cardcrawl.core.CardCrawlGame;
         import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
         import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
         import java.util.ArrayList;

 public class CloneAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
         {
       public static  String[] TEXT ;
    
       private static final float DURATION_PER_CARD = 0.25F;
       private AbstractPlayer p;
       private int dupeAmount = 1;
       private ArrayList<AbstractCard> cannotDuplicate = new ArrayList();

             static {
                 TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;
             }

       public CloneAction(AbstractCreature source)
       {
             setValues(AbstractDungeon.player, source);
             this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DRAW;
             this.duration = 0.25F;
             this.p = AbstractDungeon.player;
             this.dupeAmount = 1;
           }
    
       public void update()
       {
             int i;
             if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST)
                 {
                   for (AbstractCard c : this.p.hand.group) {
                         if (!isDualWieldable(c)) {
                               this.cannotDuplicate.add(c);
                             }
                       }

            
                   if (this.cannotDuplicate.size() == this.p.hand.group.size()) {
                         this.isDone = true;
                         return;
                       }
            
                   if (this.p.hand.group.size() - this.cannotDuplicate.size() == 1) {
                         for (AbstractCard c : this.p.hand.group) {
                               if (isDualWieldable(c)) {
                                   AbstractDungeon.actionManager.addToTop(new PlaceActualCardIntoStasis(c
                                           .makeStatEquivalentCopy()));
                                     this.isDone = true;
                                     return;
                                   }
                             }
                       }
            
                   if (this.p.hand.group.size() > 1) {
                         AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                         tickDuration();
                         return; }
                   if (this.p.hand.group.size() == 1) {
                       AbstractDungeon.actionManager.addToTop(new PlaceActualCardIntoStasis(this.p.hand
                               .getTopCard().makeStatEquivalentCopy()));
                         returnCards();
                         this.isDone = true;
                       }
                 }
        
        
             if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                   for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                         AbstractDungeon.actionManager.addToTop(new PlaceActualCardIntoStasis(c.makeStatEquivalentCopy()));
                       }
            
                   returnCards();
            
                   AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                   AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                   this.isDone = true;
                 }
        
             tickDuration();
           }
    
       private void returnCards() {
             for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                   this.p.hand.addToTop(c);
                 }
             this.p.hand.refreshHandLayout();
           }
    
       private boolean isDualWieldable(AbstractCard card) {
             return true;
           }
     }


