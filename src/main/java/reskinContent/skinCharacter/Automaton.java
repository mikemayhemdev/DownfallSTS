 package reskinContent.skinCharacter;

 import automaton.AutomatonChar;
 import com.badlogic.gdx.graphics.Texture;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import guardian.GuardianMod;
 import reskinContent.reskinContent;
 import reskinContent.vfx.ReskinUnlockedTextEffect;
 import sneckomod.TheSnecko;

 public  class Automaton extends AbstractSkinCharacter {


    public Automaton() {
        super();
        this.original_IMG = ImageMaster.loadImage("bronzeResources/images/charSelect/charBG.png");
        this.portrait_waifu =  ImageMaster.loadImage(reskinContent.assetPath("img/BronzeMod/portrait_waifu.png"));
        this.portrait_waifu2 =  ImageMaster.loadImage(reskinContent.assetPath("img/BronzeMod/portrait_waifu2.png"));

        this.ID = CardCrawlGame.languagePack.getCharacterString("bronze:theAutomaton").NAMES[0];
        this.NAME = CardCrawlGame.languagePack.getUIString("reskinContent:ReSkinBronze").TEXT[0];

        this.portraitAtlasPath = reskinContent.assetPath("img/BronzeMod/animation/GuardianChan_portrait");
    }

     @Override
     public void checkUnlock() {
         if (AbstractDungeon.player.chosenClass == AutomatonChar.Enums.THE_AUTOMATON && !this.reskinUnlock){
             AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(5));
             this.reskinUnlock = true;
         }
     }

     @Override
     public void InitializeStaticPortraitVar() {

     }
 }


