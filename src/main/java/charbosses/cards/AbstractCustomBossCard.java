package charbosses.cards;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.TextureLoader;

public abstract class AbstractCustomBossCard extends AbstractBossCard{


    public AbstractCustomBossCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                                  CardColor color, CardRarity rarity, CardTarget target){
        super(id.replace("downfall_Charboss:","downfall_Charboss:downfall:"), name, img, cost, rawDescription, type, color, rarity, target);
        this.loadJokeCardImage();
    }

    public AbstractCustomBossCard(String id, String name, String img, int cost, String rawDescription, CardType type,
                                  CardColor color, CardRarity rarity, CardTarget target,AbstractMonster.Intent intent){
        super(id.replace("downfall_Charboss:","downfall_Charboss:downfall:"), name, img, cost, rawDescription, type, color, rarity, target,intent);
        this.loadJokeCardImage();
    }


    public void loadJokeCardImage() {
        Texture cardTexture;
        cardTexture = TextureLoader.getTexture(this.assetUrl.replace(".png","_b.png"));
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivate(this, AbstractCard.class, "jokePortrait", cardImg);
    }



}