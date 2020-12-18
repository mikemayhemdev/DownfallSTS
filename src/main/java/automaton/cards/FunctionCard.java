package automaton.cards;

import automaton.cardmods.CardEffectsCardMod;
import basemod.ReflectionHacks;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class FunctionCard extends AbstractBronzeCard {
    public final static String ID = makeID("FunctionCard");

    public final static String RIBBON_COLOR = "#198a2a";

    public FunctionCard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);
        setPortraitTextures("bronzeResources/images/512/frame_function.png", "bronzeResources/images/1024/frame_function.png");
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    @Override
    public void upp() {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        //No Stack Overflow here.
    }

    @SpireOverride
    protected void renderBannerImage(SpriteBatch sb, float x, float y) {
        Color blah = (Color) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderColor");
        ReflectionHacks.setPrivate(this, AbstractCard.class, "renderColor", Color.valueOf(RIBBON_COLOR));
        SpireSuper.call(sb, x, y);
        ReflectionHacks.setPrivate(this, AbstractCard.class, "renderColor", blah);
    }

    public ArrayList<AbstractCard> cards() {
        ArrayList<AbstractCard> mCardList = new ArrayList<>();
        for (AbstractCardModifier m : CardModifierManager.getModifiers(this, CardEffectsCardMod.ID)) {
            if (m instanceof CardEffectsCardMod) {
                mCardList.add(((CardEffectsCardMod) m).stored);
            }
        }
        return mCardList;
    }

    //Welcome to the tough part
    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {
        int x = cards().size();
        switch (x) {
            case 0: {
                SpireSuper.call(sb);
                break;
            }
            case 1: {
                float drawX;
                float drawY;

                TextureAtlas.AtlasRegion portrait0 = null;
                try {
                    Field f = AbstractCard.class.getDeclaredField("portrait");
                    f.setAccessible(true);

                    portrait0 = (TextureAtlas.AtlasRegion) f.get(cards().get(0));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

                if (portrait0 != null) {

                    portrait0 = new TextureAtlas.AtlasRegion(portrait0);
                    portrait0.setRegion(
                            portrait0.getRegionX(),
                            portrait0.getRegionY(),
                            portrait0.getRegionWidth(),
                            portrait0.getRegionHeight()
                    );

                    drawX = current_x - portrait0.packedWidth / 2f;
                    drawY = current_y - portrait0.packedHeight / 2f;
                    sb.draw(portrait0,
                            drawX, drawY + 72.0F,
                            portrait0.packedWidth / 2.0F, portrait0.packedHeight / 2.0F - 72.0F,
                            portrait0.packedWidth, portrait0.packedHeight,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );
                }
                break;
            }
            case 2: {
                float drawX;
                float drawY;

                TextureAtlas.AtlasRegion portrait0 = null;
                TextureAtlas.AtlasRegion portrait1 = null;
                try {
                    Field f = AbstractCard.class.getDeclaredField("portrait");
                    f.setAccessible(true);

                    portrait0 = (TextureAtlas.AtlasRegion) f.get(cards().get(0));
                    portrait1 = (TextureAtlas.AtlasRegion) f.get(cards().get(1));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

                if (portrait0 != null && portrait1 != null) {
                    portrait0 = new TextureAtlas.AtlasRegion(portrait0);
                    portrait0.setRegion(
                            portrait0.getRegionX(),
                            portrait0.getRegionY(),
                            portrait0.getRegionWidth() / 2,
                            portrait0.getRegionHeight()
                    );
                    portrait1 = new TextureAtlas.AtlasRegion(portrait1);
                    portrait1.setRegion(
                            portrait1.getRegionX() + portrait1.getRegionWidth() / 2,
                            portrait1.getRegionY(),
                            portrait1.getRegionWidth() / 2,
                            portrait1.getRegionHeight()
                    );

                    drawX = current_x - portrait0.packedWidth / 2f;
                    drawY = current_y - portrait0.packedHeight / 2f;
                    sb.draw(portrait0,
                            drawX, drawY + 72.0F,
                            portrait0.packedWidth / 2.0F, portrait0.packedHeight / 2.0F - 72.0F,
                            portrait0.packedWidth / 2.0f, portrait0.packedHeight,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );

                    drawX = current_x - portrait1.packedWidth / 2f;
                    drawY = current_y - portrait1.packedHeight / 2f;
                    sb.draw(portrait1,
                            drawX + (portrait1.packedWidth / 2.0f), drawY + 72.0F,
                            0, portrait1.packedHeight / 2.0F - 72.0F,
                            portrait1.packedWidth / 2.0f, portrait1.packedHeight,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );
                }
                break;
            }
            case 3: {
                float drawX;
                float drawY;

                TextureAtlas.AtlasRegion portrait0 = null;
                TextureAtlas.AtlasRegion portrait1 = null;
                TextureAtlas.AtlasRegion portrait2 = null;
                try {
                    Field f = AbstractCard.class.getDeclaredField("portrait");
                    f.setAccessible(true);

                    portrait0 = (TextureAtlas.AtlasRegion) f.get(cards().get(0));
                    portrait1 = (TextureAtlas.AtlasRegion) f.get(cards().get(1));
                    portrait2 = (TextureAtlas.AtlasRegion) f.get(cards().get(2));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

                if (portrait0 != null && portrait1 != null) {
                    portrait0 = new TextureAtlas.AtlasRegion(portrait0);
                    portrait0.setRegion(
                            portrait0.getRegionX(),
                            portrait0.getRegionY(),
                            portrait0.getRegionWidth() / 3,
                            portrait0.getRegionHeight()
                    );
                    portrait1 = new TextureAtlas.AtlasRegion(portrait1);
                    portrait1.setRegion(
                            portrait1.getRegionX() + portrait1.getRegionWidth() / 3,
                            portrait1.getRegionY(),
                            portrait1.getRegionWidth() / 3,
                            portrait1.getRegionHeight()
                    );
                    portrait2 = new TextureAtlas.AtlasRegion(portrait2);
                    portrait2.setRegion(
                            portrait2.getRegionX() + ((portrait2.getRegionWidth() / 3) * 2),
                            portrait2.getRegionY(),
                            portrait2.getRegionWidth() / 3,
                            portrait2.getRegionHeight()
                    );

                    drawX = current_x - portrait0.packedWidth / 2f;
                    drawY = current_y - portrait0.packedHeight / 2f;
                    sb.draw(portrait0,
                            drawX, drawY + 72.0F,
                            portrait0.packedWidth / 2.0F, portrait0.packedHeight / 2.0F - 72.0F,
                            portrait0.packedWidth / 3.0f, portrait0.packedHeight,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );

                    drawX = (current_x - portrait1.packedWidth / 2f);
                    drawY = current_y - portrait1.packedHeight / 2f;
                    sb.draw(portrait1,
                            drawX + (portrait1.packedWidth / 3.0f), drawY + 72.0F,
                            portrait1.packedWidth / 6.0F, portrait1.packedHeight / 2.0F - 72.0F,
                            portrait1.packedWidth / 3.0f, portrait1.packedHeight,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );

                    drawX = (current_x - portrait2.packedWidth / 2f);
                    drawY = current_y - portrait2.packedHeight / 2f;
                    sb.draw(portrait2,
                            drawX + ((portrait2.packedWidth / 3.0f) * 2.0f), drawY + 72.0F,
                            -(portrait2.packedWidth / 6.0f), portrait2.packedHeight / 2.0F - 72.0F,
                            portrait2.packedWidth / 3.0f, portrait2.packedHeight,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );

                }
                break;//Once I figure out how to step from 1/3 of card 1 to 1/3 of card 2, it's pretty easy to do 2->3. But first that
            }
            case 4: {
                float drawX;
                float drawY;

                TextureAtlas.AtlasRegion portrait0 = null;
                TextureAtlas.AtlasRegion portrait1 = null;
                TextureAtlas.AtlasRegion portrait2 = null;
                TextureAtlas.AtlasRegion portrait3 = null;
                try {
                    Field f = AbstractCard.class.getDeclaredField("portrait");
                    f.setAccessible(true);

                    portrait0 = (TextureAtlas.AtlasRegion) f.get(cards().get(0));
                    portrait1 = (TextureAtlas.AtlasRegion) f.get(cards().get(1));
                    portrait2 = (TextureAtlas.AtlasRegion) f.get(cards().get(2));
                    portrait3 = (TextureAtlas.AtlasRegion) f.get(cards().get(3));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }

                if (portrait0 != null && portrait1 != null) {
                    portrait0 = new TextureAtlas.AtlasRegion(portrait0);
                    portrait0.setRegion(
                            portrait0.getRegionX(),
                            portrait0.getRegionY(),
                            portrait0.getRegionWidth() / 2,
                            portrait0.getRegionHeight() / 2
                    );
                    portrait1 = new TextureAtlas.AtlasRegion(portrait1);
                    portrait1.setRegion(
                            portrait1.getRegionX() + portrait1.getRegionWidth() / 2,
                            portrait1.getRegionY(),
                            portrait1.getRegionWidth() / 2,
                            portrait1.getRegionHeight() / 2
                    );
                    portrait2 = new TextureAtlas.AtlasRegion(portrait2);
                    portrait2.setRegion(
                            portrait2.getRegionX() + portrait2.getRegionWidth() / 2,
                            portrait2.getRegionY() + portrait2.getRegionHeight() / 2,
                            portrait2.getRegionWidth() / 2,
                            portrait2.getRegionHeight() / 2
                    );
                    portrait3 = new TextureAtlas.AtlasRegion(portrait3);
                    portrait3.setRegion(
                            portrait3.getRegionX() + portrait3.getRegionWidth() / 2,
                            portrait3.getRegionY() + portrait3.getRegionHeight() / 2,
                            portrait3.getRegionWidth() / 2,
                            portrait3.getRegionHeight() / 2
                    );

                    drawX = current_x - portrait0.packedWidth / 2f;
                    drawY = current_y;
                    sb.draw(portrait0,
                            drawX, drawY + 72.0F,
                            portrait0.packedWidth / 2.0F, portrait0.packedHeight / 2F - 72F,
                            portrait0.packedWidth / 2.0f, portrait0.packedHeight / 2F,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );

                    drawX = current_x - portrait1.packedWidth / 2f;
                    drawY = current_y;
                    sb.draw(portrait1,
                            drawX + (portrait1.packedWidth / 2.0f), drawY + 72.0F,
                            0, portrait0.packedHeight - 72F,
                            portrait1.packedWidth / 2.0f, portrait1.packedHeight / 2F,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );

/*
                    drawX = current_x - portrait2.packedWidth / 2f;
                    drawY = current_y - portrait2.packedHeight / 2F;
                    sb.draw(portrait2,
                            drawX, drawY + 72F,
                            portrait2.packedWidth / 2.0F, portrait2.packedHeight / 2.0F - 36F,
                            portrait2.packedWidth / 2.0f, portrait2.packedHeight / 2.0F,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );

                    drawX = current_x - portrait3.packedWidth / 2f;
                    drawY = current_y - portrait3.packedHeight / 2F;
                    sb.draw(portrait3,
                            drawX + (portrait3.packedWidth / 2.0f), drawY + 72F,
                            portrait0.packedWidth / 2.0F, portrait3.packedHeight / 2.0F - 36F,
                            portrait3.packedWidth / 2.0f, portrait3.packedHeight / 2.0F,
                            drawScale * Settings.scale, drawScale * Settings.scale,
                            angle
                    );
                }
                */
                    break;
                }

            }
        }
    }
}
