  package reskinContent.vfx;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.graphics.g2d.TextureAtlas;
 import com.badlogic.gdx.math.MathUtils;
 import com.esotericsoftware.spine.*;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.helpers.ImageMaster;
 import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
 import reskinContent.reskinContent;

 import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

  public class SlimedScreenEffect extends AbstractGameEffect {

   private float speed;

   private static TextureAtlas Atlas = null ;
   private static Skeleton Skeleton;
   private static AnimationState State ;
   private static AnimationStateData StateData;
   private static SkeletonData Data;

   public SlimedScreenEffect(float speed) {
     this.speed = speed;
     this.duration = 3.0f;

     loadanimation(this.speed);
   }

    public SlimedScreenEffect() {
      this(1.0f);
    }
    
   private static void loadanimation(float timeScale){
     Atlas = new TextureAtlas(Gdx.files.internal(reskinContent.assetPath("img/Slimebound/Slaifu/vfx/slimed_the_screen.atlas")));
     SkeletonJson json = new SkeletonJson(Atlas);
     json.setScale(Settings.scale / 1.0F);
     Data = json.readSkeletonData(Gdx.files.internal(reskinContent.assetPath("img/Slimebound/Slaifu/vfx/slimed_the_screen.json")));


     Skeleton = new Skeleton(Data);
     Skeleton.setColor(Color.WHITE);
     StateData = new AnimationStateData(Data);
     State = new AnimationState(StateData);
     StateData.setDefaultMix(0.2F);

     State.setTimeScale(1.0f * timeScale);
     Skeleton.setPosition(Settings.WIDTH/ 2.0f, Settings.HEIGHT / 2.0f);

       State.setAnimation(1, "spot", false);
       State.setAnimation(0, "slide", false);
   }


   
   
   public void update() {

     this.duration -= Gdx.graphics.getDeltaTime();
     if (this.duration < 0.0F) {
       this.isDone = true;
     }
   }
   
   public void render(SpriteBatch sb) {
     State.update(Gdx.graphics.getDeltaTime());
     State.apply(Skeleton);
     Skeleton.updateWorldTransform();
     Skeleton.setColor(Color.WHITE);
     Skeleton.setFlip(false,false);

     
     sb.end();
     CardCrawlGame.psb.begin();
     sr.draw(CardCrawlGame.psb, Skeleton);
     CardCrawlGame.psb.end();
     sb.begin();
     
   }
   
   public void dispose() {}
 }


