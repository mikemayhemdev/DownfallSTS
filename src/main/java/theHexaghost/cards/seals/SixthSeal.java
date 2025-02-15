package theHexaghost.cards.seals;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.FutureUpgradePower;

// this is actually fifth seal, FifthSeal.java is sixth seal, TODO: after the loc is finished, swap the id name for clearer info in metric
@NoCompendium
public class SixthSeal extends AbstractSealCard {
    public final static String ID = makeID("SixthSeal");
    private static int count_cards = 0;
    public SixthSeal() {
        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 0;
        baseBurn = burn = 13;
        tags.add(AbstractCard.CardTags.HEALING);
        HexaMod.loadJokeCardImage(this, "SixthSeal.png");
    }

    private void count(){
        count_cards = 0;
        if(AbstractDungeon.player.masterDeck.group != null){
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {
                    count_cards++;
                }
            }
        }
    }

    @Override
    public void applyPowers() {
        count();
        int real_base_magic = baseMagicNumber;
        baseMagicNumber = magicNumber = count_cards / 13;
        if(Settings.language != Settings.GameLanguage.ZHS){
            if( magicNumber <= 1){
                this.rawDescription = this.EXTENDED_DESCRIPTION[0] + magicNumber + this.EXTENDED_DESCRIPTION[1] + DESCRIPTION;
            }else{
                this.rawDescription = this.EXTENDED_DESCRIPTION[0] + magicNumber + this.EXTENDED_DESCRIPTION[2] + DESCRIPTION;
            }
        }else{
            this.rawDescription = DESCRIPTION + this.EXTENDED_DESCRIPTION[0] + magicNumber + this.EXTENDED_DESCRIPTION[1] ;
        }

        this.initializeDescription();
//        super.applyPowers();
        baseMagicNumber = real_base_magic;
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FutureUpgradePower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
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



}