package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.cards.AbstractHexaCard;
import theHexaghost.relics.TheBrokenSeal;
import theHexaghost.vfx.BrokenSealEffect;

import java.util.ArrayList;

public abstract class AbstractSealCard extends AbstractHexaCard {
//    private String[] descriptorStrings = CardCrawlGame.languagePack.getUIString(HexaMod.makeID("SealDescriptor")).TEXT;
//    private String EtherealStrings = CardCrawlGame.languagePack.getUIString(downfallMod.makeID("EtherealMod")).TEXT[0];

    public AbstractSealCard(final String id, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        super(id, cost, type, rarity, target);
        tags.add(CardTags.HEALING);
        isEthereal = true;
    }
//
//    public void upgrade() {
//        if (!upgraded) {
//            upgradeName();
//            isEthereal = false;
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
//        }
//    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        realUse(abstractPlayer, abstractMonster);
        if (!AbstractDungeon.player.hasRelic(TheBrokenSeal.ID)) {
            ArrayList<String> sealNameList = new ArrayList<>();
            ArrayList<AbstractSealCard> seals_to_remove = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
                if (c instanceof AbstractSealCard) {
                    if (!(sealNameList.contains(c.cardID))) {
                        sealNameList.add(c.cardID);
                        seals_to_remove.add( (AbstractSealCard)c ) ;
                    }else{ // when there already is the seal in the list and you played another, replace the upgraded one with the none one
                        for(AbstractSealCard seal_to_be_replaced: seals_to_remove){ // replace seals to be removed with un-upgraded version if possible
                            if( seal_to_be_replaced.getClass().equals( c.getClass() ) ){ // if the same type of seal card
                                if( (!c.upgraded) && (seal_to_be_replaced.upgraded) ){ // only replace when necessary
                                    // replace it
                                    seals_to_remove.set( seals_to_remove.indexOf(seal_to_be_replaced), (AbstractSealCard)c ) ;
                                }
                            }
                        }
                    }
                }
            }
            if (playedAll(sealNameList)) {
                boolean first_upgraded  = false,
                        second_upgraded = false,
                        third_upgraded  = false,
                        fourth_upgraded = false,
                        fifth_upgraded  = false,
                        sixth_upgraded  = false;
                for(AbstractSealCard seal: seals_to_remove){
                    if(seal instanceof FirstSeal && seal.upgraded)       first_upgraded     = true;
                    else if(seal instanceof SecondSeal && seal.upgraded) second_upgraded    = true;
                    else if(seal instanceof ThirdSeal && seal.upgraded)  third_upgraded     = true;
                    else if(seal instanceof FourthSeal && seal.upgraded) fourth_upgraded    = true;
                    else if(seal instanceof SixthSeal && seal.upgraded)  fifth_upgraded     = true;
                    else if(seal instanceof FifthSeal && seal.upgraded)  sixth_upgraded     = true;
                }

                AbstractDungeon.actionManager.cardsPlayedThisCombat.removeIf(c -> c instanceof AbstractSealCard);
                ArrayList<AbstractCard> seals_to_remove_for_real = new ArrayList<>();
                for(AbstractSealCard seal : seals_to_remove){
                    for(AbstractCard c : abstractPlayer.masterDeck.group){
                        if ( c.uuid == seal.uuid ){
                            seals_to_remove_for_real.add(c);
                        }
                    }
                }

                abstractPlayer.masterDeck.group.removeAll(seals_to_remove_for_real);



                addToTop(new VFXAction(new BrokenSealEffect(first_upgraded,second_upgraded,third_upgraded,fourth_upgraded,fifth_upgraded,sixth_upgraded) ));
            }
        }
    }


    public static boolean playedAll(ArrayList<String> sList) {
        return (sList.contains(FirstSeal.ID) && sList.contains(SecondSeal.ID) && sList.contains(ThirdSeal.ID) && sList.contains(FourthSeal.ID) && sList.contains(FifthSeal.ID) && sList.contains(SixthSeal.ID));
    }

    public abstract void realUse(AbstractPlayer p, AbstractMonster m);
}
